package pe.edu.upc.prestasim.network;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.prestasim.models.Request;
import pe.edu.upc.prestasim.models.User;

/**
 * Created by Cesar on 23/04/2017.
 */

public class BackendApi {

    //private static final String API_BASE = "http://movilesupc.ddns.net:7001/prestasim/";
    private static final String API_BASE = "http://192.168.1.4:7001/prestasim/";
    public static final String LOGIN = API_BASE + "login";
    public static final String USERS = API_BASE + "usuarios";
    public static final String PAYMENT_RANKS = API_BASE + "paymentRanks";
    public static final String LOAN_TYPES = API_BASE + "loanTypes";
    public static final String API_OK_CODE = "000";
    private static String TAG = "BACKEND_API";


    public static JSONObject createLoginReq(String dni, String password){
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("dni", dni);
            requestObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    public static JSONObject createRegisterUserReq(User user){
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("name", user.getName());
            requestObject.put("authorization", user.getAuthorization());
            requestObject.put("birth_date", user.getBirthDate());
            requestObject.put("dni", user.getDni());
            requestObject.put("email", user.getEmail());
            requestObject.put("id_payment_rank", user.getIdPaymentRank());
            requestObject.put("password", user.getPassword());
            requestObject.put("phone_number", user.getPhoneNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    public static String generateRequestsUrl(Integer idUser){
        return USERS + "/" + idUser + "/solicitudes";
    }

    public static JSONObject createRegisterRequestReq(Request request){
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("amount",request.getAmount());
            requestObject.put("id_loan_type",request.getIdLoanType());
            requestObject.put("id_user",request.getIdUser());
            requestObject.put("installments",request.getInstallments());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

}
