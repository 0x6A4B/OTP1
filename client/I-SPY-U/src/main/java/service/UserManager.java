package service;

import http.UserQuery;
import model.User;
import util.ConfigSingleton;
import util.Trace;

public class UserManager {
    private final UserQuery userQuery;

    public UserManager(){
         userQuery = new UserQuery();
    }

    public User login(User user){
        Trace.out(Trace.Level.DEV, ("usermg.login"));
        return userQuery.login(user);
    }

    public User register(User user){
        return userQuery.register(user);
    }

    public User logout(User user){
        // Let's reset token
        ConfigSingleton.getInstance().setToken("");
        // TODO: Should we also remove from config file?
        return null;
    }

    // TODO: Should we implement this?
    public User remove(User user){
        return null;
    }

    // TODO: Implement this in API also
    public User update(User user){
        return null;
    }

}
