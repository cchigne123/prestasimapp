package pe.edu.upc.prestasim.models;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.prestasim.utils.Utilities;

/**
 * Created by Cesar on 23/04/2017.
 */
public class User extends SugarRecord {

    private Integer idUser;
    private String name;
    private String dni;
    private String password;
    private String phoneNumber;
    private String email;
    private String birthDate;
    private Integer idPaymentRank;
    private String authorization;

    public User(){}

    public Integer getIdUser() {
        return idUser;
    }

    public User setIdUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getDni() {
        return dni;
    }

    public User setDni(String dni) {
        this.dni = dni;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public User setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Integer getIdPaymentRank() {
        return idPaymentRank;
    }

    public User setIdPaymentRank(Integer idPaymentRank) {
        this.idPaymentRank = idPaymentRank;
        return this;
    }

    public String getAuthorization() {
        return authorization;
    }

    public User setAuthorization(String authorization) {
        this.authorization = authorization;
        return this;
    }

    public String validateNew(){
        if(Utilities.isNullOrEmpty(this.name)
                || Utilities.isNullOrEmpty(this.password)
                || Utilities.isNullOrEmpty(this.dni)
                || Utilities.isNullOrEmpty(this.email)
                || Utilities.isNullOrEmpty(this.birthDate)
                || Utilities.isNullOrEmpty(this.phoneNumber)
                || Utilities.isNullOrEmpty(this.idPaymentRank)){
            return "Complete todos los datos y vuelva a intentarlo";
        }
        if(!Utilities.isDateWithCorrectFormat(this.birthDate)){
            return "La fecha debe estar en el formato dd/MM/yyyy";
        }
        return null;
    }

    public static User build(JSONObject jsonObject){
        if(jsonObject == null) return null;
        User user = new User();
        try {
            user.setName(jsonObject.getString("name"))
                .setIdPaymentRank(jsonObject.getInt("id_payment_rank"))
                .setEmail(jsonObject.getString("email"))
                .setDni(jsonObject.getString("dni"))
                .setPhoneNumber(jsonObject.getString("phone_number"))
                .setBirthDate(jsonObject.getString("birth_date"))
                .setIdUser(jsonObject.getInt("id_user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

}
