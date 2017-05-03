package pe.edu.upc.prestasim.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import pe.edu.upc.prestasim.PrestasimApplication;
import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.activities.MenuActivity;
import pe.edu.upc.prestasim.activities.RegisterActivity;
import pe.edu.upc.prestasim.models.LoanType;
import pe.edu.upc.prestasim.models.Request;
import pe.edu.upc.prestasim.models.RequestTax;
import pe.edu.upc.prestasim.models.User;
import pe.edu.upc.prestasim.network.BackendApi;
import pe.edu.upc.prestasim.services.MasterService;
import pe.edu.upc.prestasim.services.RequestService;
import pe.edu.upc.prestasim.services.UserService;
import pe.edu.upc.prestasim.utils.Utilities;

public class RequestFragment extends Fragment {

    private View mView;
    private EditText amountET;
    private Spinner loanTypeSpinner, installmentSpinner;
    private MasterService masterService;
    private UserService userService;
    private RequestService requestService;
    private ProgressDialog mProgressDialog;
    private Button btnRegister;
    private User user;
    Map<Integer, Integer> spinnerMap;

    public static RequestFragment newInstance() {
        return new RequestFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_request, container, false);

        masterService = ((PrestasimApplication) getActivity().getApplication()).getMasterService();
        userService = ((PrestasimApplication) getActivity().getApplication()).getUserService();
        requestService = ((PrestasimApplication) getActivity().getApplication()).getRequestService();
        amountET = (EditText) mView.findViewById(R.id.amountET);
        loanTypeSpinner = (Spinner) mView.findViewById(R.id.loanTypeSpinner);
        installmentSpinner = (Spinner) mView.findViewById(R.id.installmentSpinner);
        btnRegister = (Button) mView.findViewById(R.id.btnRegister);
        mProgressDialog = new ProgressDialog(mView.getContext());
        user = userService.obtainCurrentUser();
        fillLoanTypes();
        populateCommitments();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = amountET.getText().toString();
                String installment = installmentSpinner.getSelectedItem().toString();
                if(!Utilities.isNullOrEmpty(amount)){
                    Request request = new Request()
                            .setAmount(Double.parseDouble(amount))
                            .setId_loan_type(spinnerMap.get(loanTypeSpinner.getSelectedItemPosition()))
                            .setId_user(user.getId_user())
                            .setInstallments(Integer.valueOf(installment));
                    registerRequestOnApi(request);
                } else {
                    Toast.makeText(mView.getContext(),
                            getString(R.string.reg_err_empty_amount), Toast.LENGTH_LONG).show();
                }
            }
        });

        return mView;
    }

    private void registerRequestOnApi(Request request) {
        showLoader();
        AndroidNetworking.post(BackendApi.generateRegisterRequestUrl(request))
                .addJSONObjectBody(BackendApi.createRegisterRequestReq(request))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideLoader();
                        Log.i(getString(R.string.tag), "response: " + response);
                        try {
                            Type listType = new TypeToken<List<RequestTax>>() {}.getType();
                            if(BackendApi.API_OK_CODE.equals(response.getString("coderesult"))){
                                Request newRequest = new Gson()
                                        .fromJson(response.getString("request"), Request.class);
                                requestService.registerRequest(newRequest);
                                List<RequestTax> options = new Gson()
                                        .fromJson(response.getJSONObject("request")
                                                .getString("options"), listType);
                                for(RequestTax option : options){
                                    requestService.registerRequestTax(option);
                                }
                                showRequestOptions(options);
                            } else {
                                Toast.makeText(getView().getContext(),
                                        response.getString("msgresult"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hideLoader();
                        Log.e(getString(R.string.tag), "error: " + anError.toString());
                    }
                });
    }

    private void showRequestOptions(List<RequestTax> options) {

    }

    private void populateCommitments() {
        String[] spinnerArray = {"6","12","18","24","36","48","60","180","240","300"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        installmentSpinner.setAdapter(adapter);
    }

    private void fillLoanTypes() {
        List<LoanType> types = masterService.obtainLoanTypes();
        if(types.isEmpty()){
            showLoader();
            AndroidNetworking.get(BackendApi.LOAN_TYPES)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            hideLoader();
                            Log.i(getString(R.string.tag), "response: " + response);
                            Type listType = new TypeToken<List<LoanType>>() {}.getType();
                            try {
                                if(BackendApi.API_OK_CODE.equals(response.getString("coderesult"))){
                                    List<LoanType> types = new Gson().fromJson
                                            (response.getString("loanTypes"), listType);
                                    for (LoanType type : types) {
                                        masterService.saveLoanType(type);
                                    }
                                    populateLoanTypes(types);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            hideLoader();
                            Log.e(getString(R.string.tag), "error: " + anError.toString());
                        }
                    });
        } else {
            populateLoanTypes(types);
        }
    }

    private void populateLoanTypes(List<LoanType> types) {
        String[] spinnerArray = new String[types.size()];
        spinnerMap = new HashMap<>();
        for(int i=0;i<types.size();i++){
            LoanType type = types.get(i);
            spinnerMap.put(i, type.getId_loan_type());
            spinnerArray[i] = type.getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loanTypeSpinner.setAdapter(adapter);
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

}
