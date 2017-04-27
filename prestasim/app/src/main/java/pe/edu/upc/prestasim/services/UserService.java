package pe.edu.upc.prestasim.services;

import com.orm.query.Condition;
import com.orm.query.Select;

import pe.edu.upc.prestasim.models.User;

/**
 * Created by cesar.chigne on 4/24/2017.
 */

public class UserService {

    public boolean saveUser(User user){
        return user.save() > 0;
    }

    public User obtainCurrentUser(){
        return Select.from(User.class).first();
    }

    public boolean deleteUser(User user){
        return user.delete();
    }

}
