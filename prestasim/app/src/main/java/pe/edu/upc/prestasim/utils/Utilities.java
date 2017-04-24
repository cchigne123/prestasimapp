package pe.edu.upc.prestasim.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import pe.edu.upc.prestasim.R;

/**
 * Created by Cesar on 24/04/2017.
 */

public class Utilities {

    public static boolean isNullOrEmpty(Object value){
        if(value == null) return true;
        if(value instanceof String &&
                value.toString().isEmpty()) return true;
        return false;
    }

    public static boolean isDateWithCorrectFormat(String date){
        DateFormat dFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
        try {
            if(date.length() != 10) return false;
            dFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void updateSharedPreferences
            (Context context, String preferencesFileKey,
             int idUser, String name, String mail){
        SharedPreferences sharedPref = context.getSharedPreferences(
                preferencesFileKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Constants.CURRENT_USER_ID, idUser);
        editor.putString(Constants.CURRENT_USER_NAME, name);
        editor.putString(Constants.CURRENT_USER_MAIL, mail);
        editor.commit();
    }

}
