package pe.edu.upc.prestasim;

import com.orm.SugarApp;

import pe.edu.upc.prestasim.services.MasterService;
import pe.edu.upc.prestasim.services.RequestService;
import pe.edu.upc.prestasim.services.UserService;

/**
 * Created by Cesar on 23/04/2017.
 */

public class PrestasimApplication extends SugarApp {

    MasterService masterService = new MasterService();
    UserService userService = new UserService();
    RequestService requestService = new RequestService();

    public MasterService getMasterService() {
        return masterService;
    }

    public UserService getUserService() {
        return userService;
    }

    public RequestService getRequestService() {
        return requestService;
    }

}
