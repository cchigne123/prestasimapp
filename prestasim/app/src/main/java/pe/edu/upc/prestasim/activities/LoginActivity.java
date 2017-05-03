package pe.edu.upc.prestasim.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pe.edu.upc.prestasim.PrestasimApplication;
import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.models.User;
import pe.edu.upc.prestasim.network.BackendApi;
import pe.edu.upc.prestasim.services.UserService;

public class LoginActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private EditText etDNI, etPassword;
    private ProgressDialog mProgressDialog;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = ((PrestasimApplication) getApplication()).getUserService();
        initAndroidNetworking();
        loadUI();
        loadProgressDialog();
    }

    private void tryLogin(final View v) {
        String dni = etDNI.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (!dni.isEmpty() && !password.isEmpty()) {
            showLoader();
            AndroidNetworking.post(BackendApi.LOGIN)
                    .addJSONObjectBody(BackendApi.createLoginReq(dni, password))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            hideLoader();
                            Log.i(TAG, "response: " + response);
                            try {
                                if(BackendApi.API_OK_CODE.equals(response.getString("coderesult"))){
                                    User user = User.build(response.getJSONObject("user"));
                                    userService.saveUser(user);
                                    startActivity(new Intent(v.getContext(), MenuActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this,
                                            response.getString("msgresult"),
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(LoginActivity.this,
                                        getResources().getString(R.string.login_incorrect),
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            hideLoader();
                            Log.e(TAG, "error: " + anError.toString());
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.should_complete_fields), Toast.LENGTH_LONG)
                    .show();
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
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
    }

    private void loadUI() {
        etDNI = (EditText) findViewById(R.id.etDNI);
        etPassword = (EditText) findViewById(R.id.etPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin(v);
            }
        });

    }

    private void initAndroidNetworking() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.initialize(LoginActivity.this, okHttpClient);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
