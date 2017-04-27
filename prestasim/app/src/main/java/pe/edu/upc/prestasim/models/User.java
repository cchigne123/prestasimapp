package pe.edu.upc.prestasim.models;

import com.orm.SugarRecord;

import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.utils.Utilities;

/**
 * Created by Cesar on 23/04/2017.
 */
public class User extends SugarRecord {

    private Integer id_user;
    private String name;
    private String dni;
    private String password;
    private String phone_number;
    private String email;
    private String birth_date;
    private Integer id_payment_rank;
    private String authorization;

    public User(){}

    public Integer getId_user() {
        return id_user;
    }

    public User setId_user(Integer id_user) {
        this.id_user = id_user;
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

    public String getPhone_number() {
        return phone_number;
    }

    public User setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public User setBirth_date(String birth_date) {
        this.birth_date = birth_date;
        return this;
    }

    public Integer getId_payment_rank() {
        return id_payment_rank;
    }

    public User setId_payment_rank(Integer id_payment_rank) {
        this.id_payment_rank = id_payment_rank;
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
                || Utilities.isNullOrEmpty(this.birth_date)
                || Utilities.isNullOrEmpty(this.phone_number)
                || Utilities.isNullOrEmpty(this.id_payment_rank)){
            return "Complete todos los datos y vuelva a intentarlo";
        }
        if(!Utilities.isDateWithCorrectFormat(this.birth_date)){
            return "La fecha debe estar en el formato dd/MM/yyyy";
        }
        return null;
    }

}
