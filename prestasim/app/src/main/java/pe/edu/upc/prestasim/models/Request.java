package pe.edu.upc.prestasim.models;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by cesar.chigne on 5/2/2017.
 */

public class Request extends SugarRecord {

    private Integer id_request;
    private Integer id_user;
    private Double amount;
    private Integer installments;
    private Date registerDate;
    private Integer id_loan_type;

    public Integer getId_request() {
        return id_request;
    }
    public Request setId_request(Integer idsolicitud) {
        this.id_request = idsolicitud;
        return this;
    }
    public Integer getId_user() {
        return id_user;
    }
    public Request setId_user(Integer idusuario) {
        this.id_user = idusuario;
        return this;
    }
    public Double getAmount() {
        return amount;
    }
    public Request setAmount(Double monto) {
        this.amount = monto;
        return this;
    }
    public Integer getInstallments() {
        return installments;
    }
    public Request setInstallments(Integer plazo) {
        this.installments = plazo;
        return this;
    }
    public Integer getId_loan_type() {
        return id_loan_type;
    }
    public Request setId_loan_type(Integer idtipoprestamo) {
        this.id_loan_type = idtipoprestamo;
        return this;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
