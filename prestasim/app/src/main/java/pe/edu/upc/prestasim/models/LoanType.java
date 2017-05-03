package pe.edu.upc.prestasim.models;

import com.orm.SugarRecord;

/**
 * Created by cesar.chigne on 5/2/2017.
 */

public class LoanType extends SugarRecord {

    private int id_loan_type;
    private String name;

    public LoanType(){}

    public int getId_loan_type() {
        return id_loan_type;
    }
    public void setId_loan_type(int idtipoprestamo) {
        this.id_loan_type = idtipoprestamo;
    }
    public String getName() {
        return name;
    }
    public void setName(String nombre) {
        this.name = nombre;
    }

}
