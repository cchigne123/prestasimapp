package pe.edu.upc.prestasim.models;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.edu.upc.prestasim.utils.Constants;

/**
 * Created by cesar.chigne on 5/2/2017.
 */

public class Request extends SugarRecord {

    private Integer idRequest;
    private Integer idUser;
    private Double amount;
    private Integer installments;
    private String registerDate;
    private Integer idLoanType;

    private String loanTypeName;

    public Integer getIdRequest() {
        return idRequest;
    }
    public Request setIdRequest(Integer idsolicitud) {
        this.idRequest = idsolicitud;
        return this;
    }
    public Integer getIdUser() {
        return idUser;
    }
    public Request setIdUser(Integer idusuario) {
        this.idUser = idusuario;
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
    public Integer getIdLoanType() {
        return idLoanType;
    }
    public Request setIdLoanType(Integer idtipoprestamo) {
        this.idLoanType = idtipoprestamo;
        return this;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public Request setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public static Request build(JSONObject jsonObject){
        if(jsonObject == null) return null;
        Request request = new Request();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
            request.setIdRequest(jsonObject.getInt("id_request"))
                    .setAmount(jsonObject.getDouble("amount"))
                    .setIdLoanType(jsonObject.getInt("id_loan_type"))
                    .setIdUser(jsonObject.getInt("id_user"))
                    .setInstallments(jsonObject.getInt("installments"))
                    .setRegisterDate(simpleDateFormat.format(new Date
                            (jsonObject.getLong("register_date"))));
            if(jsonObject.has("loanTypeName")){
                request.setLoanTypeName(jsonObject.getString("loanTypeName"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    public static List<Request> build(JSONArray jsonArray){
        if(jsonArray == null) return null;
        List<Request> requests = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                requests.add(Request.build(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return requests;
    }
}
