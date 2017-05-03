package pe.edu.upc.prestasim.models;

import com.orm.SugarRecord;

/**
 * Created by Cesar on 23/04/2017.
 */

public class PaymentRank extends SugarRecord {

    private int id_payment_rank;
    private String name;
    private Double max_value;

    public int getId_payment_rank() {
        return id_payment_rank;
    }

    public PaymentRank setId_payment_rank(int idPaymentRank) {
        this.id_payment_rank = idPaymentRank;
        return this;
    }
    public String getName() {
        return name;
    }

    public PaymentRank setName(String name) {
        this.name = name;
        return this;
    }
    public Double getMax_value() {
        return max_value;
    }

    public PaymentRank setMax_value(Double maxValue) {
        this.max_value = maxValue;
        return this;
    }

}
