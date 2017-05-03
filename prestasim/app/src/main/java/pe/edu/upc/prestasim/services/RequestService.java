package pe.edu.upc.prestasim.services;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import pe.edu.upc.prestasim.models.Request;
import pe.edu.upc.prestasim.models.RequestTax;

/**
 * Created by cesar.chigne on 5/2/2017.
 */

public class RequestService {

    public boolean registerRequest(Request request){
        return request.save() > 0;
    }

    public boolean registerRequestTax(RequestTax requestTax){
        return requestTax.save() > 0;
    }

    public Request obtainRequestById(Integer id_request){
        return Select.from(Request.class).where(Condition.prop("id_request").eq(id_request)).first();
    }

    public List<RequestTax> obtainResultsByRequestId(Integer id_request){
        return Select.from(RequestTax.class)
                .where(Condition.prop("id_request").eq(id_request)).list();
    }

    public List<Request> obtainRequestsByUserId(Integer id_user){
        return Select.from(Request.class).where(Condition.prop("id_user").eq(id_user)).list();
    }

}
