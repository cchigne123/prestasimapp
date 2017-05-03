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

public class RequestTax extends SugarRecord {

    private int idRequest;
    private int idTax;
    private Double installmentAmount;
    private Double initialPayment;
    private String entityName;

    public int getIdRequest() {
        return idRequest;
    }
    public RequestTax setIdRequest(int idsolicitud) {
        this.idRequest = idsolicitud;
        return this;
    }
    public int getIdTax() {
        return idTax;
    }
    public RequestTax setIdTax(int idtasa) {
        this.idTax = idtasa;
        return this;
    }
    public Double getInstallmentAmount() {
        return installmentAmount;
    }
    public RequestTax setInstallmentAmount(Double montocuota) {
        this.installmentAmount = montocuota;
        return this;
    }
    public Double getInitialPayment() {
        return initialPayment;
    }
    public RequestTax setInitialPayment(Double cuotainicial) {
        this.initialPayment = cuotainicial;
        return this;
    }
    public String getEntityName() {
        return entityName;
    }
    public RequestTax setEntityName(String nomentidad) {
        this.entityName = nomentidad;
        return this;
    }

    public static RequestTax build(JSONObject jsonObject){
        if(jsonObject == null) return null;
        RequestTax requestTax = new RequestTax();
        try {
            requestTax.setEntityName(jsonObject.getString("entityName"))
                    .setIdRequest(jsonObject.getInt("id_request"))
                    .setIdTax(jsonObject.getInt("id_tax"))
                    .setInitialPayment(jsonObject.getDouble("initial_payment"))
                    .setInstallmentAmount(jsonObject.getDouble("installment_amount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestTax;
    }

    public static List<RequestTax> build(JSONArray jsonArray){
        if(jsonArray == null) return null;
        List<RequestTax> taxes = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                taxes.add(RequestTax.build(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return taxes;
    }

}
