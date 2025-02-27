package model;

import service.*;
import util.Trace;
import java.util.List;

public class Client {

    //private final IManager<Device, User> deviceManager = new DeviceManager();
    //private final IManager<LogEntry, Device> logManager = new LogManager();
    private final ConnectionManager connectionManager = new ConnectionManager();


    public Client(){
        Trace.out(Trace.Level.INFO, "Client instantiated");
    }

    public List<Device> getDevices(User user){
        //return service.getDevices(user);
        //return deviceManager.readAll(user);
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
        //return service.getLogEntries(device);
        //return logManager.readAll(device);
        return connectionManager.getLogEntries(device);
    }

    public boolean removeLogEntry(LogEntry logEntry){
        return false;
    }

    // USER
    public User login(User user){
        System.out.println("client.login");
        return connectionManager.login(user);
    }

    public User register(User user){
        return connectionManager.register(user);
    }
}
