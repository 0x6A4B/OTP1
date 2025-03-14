package service;

import model.Device;
import model.DeviceShare;
import model.User;
import model.LogEntry;
import util.Trace;
import java.util.List;

public class ConnectionManager {
    private UserManager userManager = new UserManager();
    private DeviceManager deviceManager = new DeviceManager();
    private LogManager logManager = new LogManager();
    private DeviceShareManager deviceShareManager = new DeviceShareManager();


    public ConnectionManager(){}

    public User login(User user){
        return userManager.login(user);
    }

    public User register(User user){
        return userManager.register(user);
    }

    /* Device methods */
    public List<Device> getDevices(){
        Trace.out(Trace.Level.DEV,"conmgr.getdevices");
        return deviceManager.readAll(new Object());
    }

    public Device createDevice(Device device){
        return deviceManager.create(device);
    }

    public boolean removeDevice(Device device){
        return deviceManager.remove(device);
    }

    public Device getDevice(Long deviceId){
        return deviceManager.read(deviceId);
    }

    /* LogEntry methods */
    public List<LogEntry> getLogEntries(Device device){
        return getLogEntries(device, -1);
    }

    public List<LogEntry> getLogEntries(Device device, int limit){
        Trace.out(Trace.Level.DEV,"conmgr.getlogentries");
        Trace.out(Trace.Level.DEV, "Device: " + device.getName());
        return logManager.readAll(device, limit);
    }

    public boolean removeLogEntry(LogEntry logEntry){
        return logManager.remove(logEntry);
    }

    /* DeviceShare methods */
    public DeviceShare shareDevice(DeviceShare deviceShare){
        return deviceShareManager.create(deviceShare);
    }

    public List<DeviceShare> getDeviceShares(Device device){
        return deviceShareManager.readAll(device);
    }

    public List<DeviceShare> getDeviceShares(){
        return deviceShareManager.readAll(null);
    }

    public boolean removeDeviceShare(DeviceShare deviceShare){
        return deviceShareManager.remove(deviceShare);
    }



    public boolean removeAllDeviceShares(Device device){
        return deviceShareManager.removeAll(device);
    }

    public DeviceShare updateDeviceShare(DeviceShare deviceShare){
        return deviceShareManager.update(deviceShare);
    }

}
