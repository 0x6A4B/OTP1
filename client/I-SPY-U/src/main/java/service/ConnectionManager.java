package service;

import model.Device;
import model.User;
import model.LogEntry;
import util.Trace;
import java.util.List;

public class ConnectionManager {
    private UserManager userManager;
    private DeviceManager deviceManager = new DeviceManager();
    private LogManager logManager = new LogManager();


    public ConnectionManager(){}

    public User login(User user){
        if (userManager == null)
            userManager = new UserManager();
        userManager.login(user);
        return null;
    }

    public User register(User user){
        if (userManager == null){
            userManager = new UserManager();
        }
        userManager.register(user);
        return null;
    }

    public List<Device> getDevices(){
        System.out.println("conmgr.getdevices");
        return deviceManager.readAll(new Object());
    }

    public List<LogEntry> getLogEntries(Device device){
        System.out.println("conmgr.getlogentries");
        Trace.out(Trace.Level.DEV, "Device: " + device.getName());
        return logManager.readAll(device);
    }

    public Device createDevice(Device device){
        return deviceManager.create(device);
    }

    public boolean removeDevice(Device device){
        return deviceManager.remove(device);
    }


}
