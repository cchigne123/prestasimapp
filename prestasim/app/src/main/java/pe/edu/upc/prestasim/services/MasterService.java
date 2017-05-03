package pe.edu.upc.prestasim.services;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import pe.edu.upc.prestasim.models.LoanType;
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

    public List<LoanType> obtainLoanTypes(){
        return Select.from(LoanType.class).list();
    }

    public boolean saveLoanType(LoanType loanType){
        return loanType.save() > 0;
    }

    public LoanType obtainLoanTypeById(Integer id_loan_type){
        return Select.from(LoanType.class).where(Condition.prop("id_loan_type").eq(id_loan_type)).first();
    }

}
