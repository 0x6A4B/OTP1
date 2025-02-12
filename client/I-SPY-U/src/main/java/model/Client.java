package model;

import service.DeviceManager;
import service.IManager;
import service.LogManager;
import service.UserManager;

import java.util.List;

public class Client {
    //private final TrustMeBraWhyWouldILie service = new TrustMeBraWhyWouldILie();

    private final IManager<Device, User> deviceManager = new DeviceManager();
    private final IManager<LogEntry, Device> logManager = new LogManager();
    private final UserManager userManager = new UserManager();


    public Client(){}

    public List<Device> getDevices(User user){
        //return service.getDevices(user);
        return deviceManager.readAll(user);
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
        return logManager.readAll(device);
    }

    // USER
    public User login(User user){
        return userManager.login(user);
    }
    public User register(User user){
        return userManager.register(user);
    }
}
