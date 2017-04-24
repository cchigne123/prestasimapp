package pe.edu.upc.prestasim;

import com.orm.SugarApp;

import pe.edu.upc.prestasim.services.MasterService;

/**
 * Created by Cesar on 23/04/2017.
 */

public class PrestasimApplication extends SugarApp {

    MasterService masterService = new MasterService();

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }
}
