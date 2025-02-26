package model;

import service.*;

import java.util.List;

public class Client {
    //private final TrustMeBraWhyWouldILie service = new TrustMeBraWhyWouldILie();

    private final IManager<Device, User> deviceManager = new DeviceManager();
    private final IManager<LogEntry, Device> logManager = new LogManager();
    //private final UserManager userManager = new UserManager();
    private final ConnectionManager connectionManager = new ConnectionManager();


    public Client(){}

    public List<Device> getDevices(User user){
        //return service.getDevices(user);
        //return deviceManager.readAll(user);
        return connectionManager.getDevices();
    }

    public Device addDevice(Device device){
        return deviceManager.create(device);
    }

    public boolean removeDevice(Device device){
        return deviceManager.remove(device);
    }

    // LOG
    public List<LogEntry> getLogEntries(Device device){
        //return service.getLogEntries(device);
        //return logManager.readAll(device);
        return null;
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
