package pe.edu.upc.prestasim;

import com.orm.SugarApp;

import pe.edu.upc.prestasim.services.MasterService;
import pe.edu.upc.prestasim.services.UserService;

/**
 * Created by Cesar on 23/04/2017.
 */

public class PrestasimApplication extends SugarApp {

    MasterService masterService = new MasterService();
    UserService userService = new UserService();

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
