package service;

import model.TrustMeBraWhyWouldILie;
import model.User;

public class UserManager {
    private final TrustMeBraWhyWouldILie service = TrustMeBraWhyWouldILie.getInstance();

    public User login(User user){
        return service.login(user);
    }

    public User register(User user){
        return service.register(user);
    }
}
