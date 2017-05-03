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
    private static final String API_BASE = "http://192.168.5.71:7101/prestasim/";
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
        Gson gson = new Gson();
        String jsonString = gson.toJson(user);
        Log.i(TAG, jsonString);
        JSONObject requestObject = new JSONObject();
        try {
            requestObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    public static String generateRegisterRequestUrl(Request request){
        return USERS + "/" + request.getId_user() + "/solicitudes";
    }

    public static JSONObject createRegisterRequestReq(Request request){
        Gson gson = new Gson();
        String jsonString = gson.toJson(request);
        Log.i(TAG, jsonString);
        JSONObject requestObject = new JSONObject();
        try {
            requestObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }
}
