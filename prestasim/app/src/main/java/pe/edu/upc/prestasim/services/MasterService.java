package pe.edu.upc.prestasim.services;

import com.orm.query.Select;

import java.util.List;

import pe.edu.upc.prestasim.models.PaymentRank;

/**
 * Created by Cesar on 23/04/2017.
 */

public class MasterService {

    public List<PaymentRank> obtainPaymentRanks(){
        return Select.from(PaymentRank.class).list();
    }

    public boolean savePaymentRank(PaymentRank paymentRank){
        return paymentRank.save() > 0;
    }

}
