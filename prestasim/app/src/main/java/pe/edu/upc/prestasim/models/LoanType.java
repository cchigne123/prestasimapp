package pe.edu.upc.prestasim.models;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cesar.chigne on 5/2/2017.
 */

public class LoanType extends SugarRecord {

    private int idLoanType;
    private String name;

    public LoanType(){}

    public int getIdLoanType() {
        return idLoanType;
    }
    public LoanType setIdLoanType(int idtipoprestamo) {
        this.idLoanType = idtipoprestamo;
        return this;
    }
    public String getName() {
        return name;
    }
    public LoanType setName(String nombre) {
        this.name = nombre;
        return this;
    }

    public static LoanType build(JSONObject jsonObject){
        if(jsonObject == null) return null;
        LoanType loanType = new LoanType();
        try {
            loanType.setName(jsonObject.getString("name"))
                    .setIdLoanType(jsonObject.getInt("id_loan_type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return loanType;
    }

    public static List<LoanType> build(JSONArray jsonArray){
        if(jsonArray == null) return null;
        List<LoanType> types = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                types.add(LoanType.build(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return types;
    }

}
