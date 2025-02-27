package model;

import service.*;
import util.ConfigSingleton;
import util.Trace;
import java.util.List;

public class Client {
    private final ConnectionManager connectionManager = new ConnectionManager();


    public Client(){
        while(!ConfigSingleton.getInstance().configLoaded())
            Trace.out(Trace.Level.DEV, "Loading properties...");

        Trace.out(Trace.Level.INFO, "Client instantiated");
    }


    public List<Device> getDevices(User user){
        return connectionManager.getDevices();
    }

    public Device addDevice(Device device){
        return connectionManager.createDevice(device);
    }

    public boolean removeDevice(Device device){
        return connectionManager.removeDevice(device);
    }

    // LOG
    public List<LogEntry> getLogEntries(Device device){
        return connectionManager.getLogEntries(device);
    }

    public boolean removeLogEntry(LogEntry logEntry){
        return connectionManager.removeLogEntry(logEntry);
    }

    // USER
    public User login(User user){
        Trace.out(Trace.Level.DEV, "client.login");
        return connectionManager.login(user);
    }

    public User register(User user){
        return connectionManager.register(user);
    }
}
