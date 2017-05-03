package pe.edu.upc.prestasim.models;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar on 23/04/2017.
 */

public class PaymentRank extends SugarRecord {

    private int idPaymentRank;
    private String name;
    private Double maxValue;

    public int getIdPaymentRank() {
        return idPaymentRank;
    }

    public PaymentRank setIdPaymentRank(int idPaymentRank) {
        this.idPaymentRank = idPaymentRank;
        return this;
    }
    public String getName() {
        return name;
    }

    public PaymentRank setName(String name) {
        this.name = name;
        return this;
    }
    public Double getMaxValue() {
        return maxValue;
    }

    public PaymentRank setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public static PaymentRank build(JSONObject jsonObject){
        if(jsonObject == null) return null;
        PaymentRank paymentRank = new PaymentRank();
        try {
            paymentRank.setName(jsonObject.getString("name"))
                    .setIdPaymentRank(jsonObject.getInt("id_payment_rank"))
                    .setMaxValue(jsonObject.getDouble("max_value"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paymentRank;
    }

    public static List<PaymentRank> build(JSONArray jsonArray){
        if(jsonArray == null) return null;
        List<PaymentRank> ranks = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                ranks.add(PaymentRank.build(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ranks;
    }

}
