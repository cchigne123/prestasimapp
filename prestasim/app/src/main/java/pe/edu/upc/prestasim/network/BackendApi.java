package pe.edu.upc.prestasim.network;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.prestasim.models.User;

/**
 * Created by Cesar on 23/04/2017.
 */

public class BackendApi {

    public static final String API_BASE = "http://192.168.1.2:7001/prestasim/";
    public static final String LOGIN = API_BASE + "login";
    public static final String USERS = API_BASE + "usuarios";
    public static final String PAYMENT_RANKS = API_BASE + "paymentRanks";
    public static final String API_OK_CODE = "000";
    private static String TAG = "BACKEND_API";


    public static JSONObject createLoginRequest(String dni, String password){
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("dni", dni);
            requestObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    public static JSONObject createRegisterUserRequest(User user){
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
}
