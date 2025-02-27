package service;

import model.Device;
import model.User;
import model.LogEntry;
import util.Trace;
import java.util.List;

public class ConnectionManager {
    private UserManager userManager = new UserManager();
    private DeviceManager deviceManager = new DeviceManager();
    private LogManager logManager = new LogManager();


    public ConnectionManager(){}

    public User login(User user){
        return userManager.login(user);
    }

    public User register(User user){
        return userManager.register(user);
    }

    public List<Device> getDevices(){
        Trace.out(Trace.Level.DEV,"conmgr.getdevices");
        return deviceManager.readAll(new Object());
    }

    public List<LogEntry> getLogEntries(Device device){
        Trace.out(Trace.Level.DEV,"conmgr.getlogentries");
        Trace.out(Trace.Level.DEV, "Device: " + device.getName());
        return logManager.readAll(device);
    }

    public boolean removeLogEntry(LogEntry logEntry){
        return logManager.remove(logEntry);
    }

    public Device createDevice(Device device){
        return deviceManager.create(device);
    }

    public boolean removeDevice(Device device){
        return deviceManager.remove(device);
    }


}
