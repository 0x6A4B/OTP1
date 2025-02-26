package service;

import http.UserQuery;
import model.TrustMeBraWhyWouldILie;
import model.User;

public class UserManager {
    private final TrustMeBraWhyWouldILie service = TrustMeBraWhyWouldILie.getInstance();
    private final UserQuery userQuery;

    public UserManager(String apiUrl){
         userQuery = new UserQuery();
    }

    public User login(User user){
        System.out.println("usermg.login");
        return userQuery.login(user);
    }

    public User register(User user){
        return userQuery.register(user);
    }

    //public User register(User user){ return UserQuery.handle("REGISTER", user); }

    public User logout(User user){
        return UserQuery.handle("LOGOUT", user);
    }

    public User remove(User user){
        return UserQuery.handle("REMOVE", user);
    }

    public User update(User user){
        return UserQuery.handle("UPDATE", user);
    }

}
