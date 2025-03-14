package model;

import service.*;
import util.ConfigSingleton;
import util.Trace;
import java.util.List;

public class Client {
    private final ConnectionManager connectionManager = new ConnectionManager();
    private boolean rememberUser;


    public Client(){
        while(!ConfigSingleton.getInstance().configLoaded())
            Trace.out(Trace.Level.DEV, "Loading properties...");

        Trace.out(Trace.Level.INFO, "Client instantiated");
    }

    /* Devices */
    public List<Device> getDevices(User user){
        return connectionManager.getDevices();
    }
    public Device addDevice(Device device){
        return connectionManager.createDevice(device);
    }
    public boolean removeDevice(Device device){
        return connectionManager.removeDevice(device);
    }
    public Device getDevice(Long deviceId){
        return connectionManager.getDevice(deviceId);
    }

    /* Shares */
    public DeviceShare shareDevice(DeviceShare deviceShare) {
        return connectionManager.shareDevice(deviceShare);
    }
    public boolean removeDeviceShare(DeviceShare deviceShare){
        return connectionManager.removeDeviceShare(deviceShare);
    }
    /* Only owner can get all shares they have made */
    public List<DeviceShare> getDeviceShares(Device device){
        return connectionManager.getDeviceShares(device);
    }
    /* All devices shared with you */
    public List<DeviceShare> getDeviceShares(){
        return connectionManager.getDeviceShares();
    }
    public DeviceShare updateDeviceShare(DeviceShare deviceShare){
        return connectionManager.updateDeviceShare(deviceShare);
    }



    /* LOG */
    // TODO: Deprecating this...??
    public List<LogEntry> getLogEntries(Device device){
        //return connectionManager.getLogEntries(device);
        return getLogEntries(device, -1);
    }

    public List<LogEntry> getLogEntries(Device device, int limit){
        return connectionManager.getLogEntries(device, limit);
    }

    public boolean removeLogEntry(LogEntry logEntry){
        return connectionManager.removeLogEntry(logEntry);
    }


    /* USER */
    public User login(User user){
        Trace.out(Trace.Level.DEV, "client.login");
        //if (ConfigSingleton.getInstance().getToken() != null && !ConfigSingleton.getInstance().getToken().isEmpty())
        //    rememberUser = true;
        User loggedUser = connectionManager.login(user);
        if (rememberUser)
            ConfigSingleton.getInstance().saveToken();
        return loggedUser;
    }
    public void setRememberUser(boolean rememberUser){
        this.rememberUser = rememberUser;
    }

    public User register(User user){
        return connectionManager.register(user);
    }
    public void logout(){
        Trace.out(Trace.Level.INFO, "User logged out");
        ConfigSingleton.getInstance().setToken("");
        ConfigSingleton.getInstance().saveToken();
    }
}
