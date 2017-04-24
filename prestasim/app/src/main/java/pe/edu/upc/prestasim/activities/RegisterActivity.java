package pe.edu.upc.prestasim.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pe.edu.upc.prestasim.PrestasimApplication;
import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.models.PaymentRank;
import pe.edu.upc.prestasim.models.User;
import pe.edu.upc.prestasim.network.BackendApi;
import pe.edu.upc.prestasim.services.MasterService;
import pe.edu.upc.prestasim.utils.Constants;
import pe.edu.upc.prestasim.utils.Utilities;

public class RegisterActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private EditText nameET, dniET, passwordET, birthDateET, emailET, phoneET;
    private CheckBox authorizeCB;
    Spinner paymentRanksSpinner;
    Map<Integer, Integer> spinnerMap;
    private ProgressDialog mProgressDialog;
    private MasterService masterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        masterService = ((PrestasimApplication) getApplication()).getMasterService();
        initAndroidNetworking();
        loadUI();
        loadProgressDialog();
        fillPaymentRanks();
    }

    private void fillPaymentRanks() {
        List<PaymentRank> paymentRanks = masterService.obtainPaymentRanks();
        if(paymentRanks.isEmpty()){
            showLoader();
            AndroidNetworking.get(BackendApi.PAYMENT_RANKS)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            hideLoader();
                            Log.i(TAG, "response: " + response);
                            Type listType = new TypeToken<List<PaymentRank>>() {}.getType();
                            try {
                                List<PaymentRank> paymentRankList = new Gson().fromJson
                                        (response.getString("paymentRanks"), listType);
                                for(PaymentRank paymentRank : paymentRankList){
                                    masterService.savePaymentRank(paymentRank);
                                }
                                populatePaymentRanks(paymentRankList);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            hideLoader();
                            Log.e(TAG, "error: " + anError.toString());
                        }
                    });
        } else {
            populatePaymentRanks(paymentRanks);
        }
    }

    private void populatePaymentRanks(List<PaymentRank> paymentRanks) {
        String[] spinnerArray = new String[paymentRanks.size()];
        spinnerMap = new HashMap<>();
        for(int i=0;i<paymentRanks.size();i++){
            PaymentRank paymentRank = paymentRanks.get(i);
            spinnerMap.put(i, paymentRank.getId_payment_rank());
            spinnerArray[i] = paymentRank.getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentRanksSpinner.setAdapter(adapter);
    }

    private void tryRegister(final View v) {
        User user = new User()
                .setName(nameET.getText().toString().trim())
                .setAuthorization(authorizeCB.isChecked()?"1":"0")
                .setBirth_date(birthDateET.getText().toString().trim())
                .setDni(dniET.getText().toString().trim())
                .setEmail(emailET.getText().toString().trim())
                .setId_payment_rank(spinnerMap.get(paymentRanksSpinner.getSelectedItemPosition()))
                .setPassword(passwordET.getText().toString().trim())
                .setPhone_number(phoneET.getText().toString().trim());
        String validationMessage = user.validateNew();
        if (Utilities.isNullOrEmpty(validationMessage)) {
            showLoader();
            AndroidNetworking.post(BackendApi.USERS)
                    .addJSONObjectBody(BackendApi.createRegisterUserRequest(user))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            hideLoader();
                            Log.i(TAG, "response: " + response);
                            try {
                                if(BackendApi.API_OK_CODE.equals(response.getString("coderesult"))){
                                    User createdUser = new Gson().fromJson(response.getString("user"), User.class);
                                    Utilities.updateSharedPreferences(v.getContext(),
                                            getString(R.string.preference_file_key),
                                            createdUser.getId_user(), createdUser.getName(), createdUser.getEmail());
                                    startActivity(new Intent(v.getContext(), MenuActivity.class));
                                } else {
                                    Toast.makeText(RegisterActivity.this,
                                            response.getString("msgresult"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            hideLoader();
                            Log.e(TAG, "error: " + anError.toString());
                        }
                    });
        } else {
            Toast.makeText(RegisterActivity.this, validationMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void showLoader() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    private void hideLoader() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void loadProgressDialog() {
        mProgressDialog = new ProgressDialog(RegisterActivity.this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
    }

    private void loadUI() {
        nameET = (EditText) findViewById(R.id.nameET);
        dniET = (EditText) findViewById(R.id.dniET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        birthDateET = (EditText) findViewById(R.id.phoneET);
        authorizeCB = (CheckBox) findViewById(R.id.authorizeCB);
        birthDateET = (EditText) findViewById(R.id.birthDateET);
        emailET = (EditText) findViewById(R.id.emailET);
        phoneET = (EditText) findViewById(R.id.phoneET);
        paymentRanksSpinner = (Spinner) findViewById(R.id.paymentRanksSpinner);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryRegister(v);
            }
        });

    }

    private void initAndroidNetworking() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.initialize(RegisterActivity.this, okHttpClient);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

}
