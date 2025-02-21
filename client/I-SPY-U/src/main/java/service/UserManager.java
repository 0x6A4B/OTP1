package service;

import http.UserQuery;
import model.TrustMeBraWhyWouldILie;
import model.User;

public class UserManager {
    private final TrustMeBraWhyWouldILie service = TrustMeBraWhyWouldILie.getInstance();
    private final UserQuery userQuery = new UserQuery();

    public User login(User user){return service.login(user);}

    public User register(User user){ return UserQuery.handle("REGISTER", user); }

    public boolean logout(User user){
        return UserQuery.handle("LOGOUT", user);
    }

    public boolean remove(User user){
        return UserQuery.handle("REMOVE", user);
    }

    public User update(User user){
        return UserQuery.handle("UPDATE", user);
    }

}
