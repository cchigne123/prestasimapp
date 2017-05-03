package pe.edu.upc.prestasim.services;

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

}
