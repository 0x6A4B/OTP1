package service;

import model.Device;
import model.DeviceShare;
import model.User;
import model.LogEntry;
import util.Trace;
import java.util.List;

/**
 * ConnectionManager is a facade that provides a simplified interface to the underlying managers.
 *
 * @author 0x6A4B, woues1
 *
 * */

public class ConnectionManager {
    private UserManager userManager = new UserManager();
    private DeviceManager deviceManager = new DeviceManager();
    private LogManager logManager = new LogManager();
    private DeviceShareManager deviceShareManager = new DeviceShareManager();


    public ConnectionManager(){}

    /**
     * Forwards the login request to the UserManager.
     *
     * @param user The user to login.
     * @return The logged in user.
     * */
    public User login(User user){
        return userManager.login(user);
    }

    /**
     * Forwards the logout request to the UserManager.
     *
     * @param user The user to logout.
     * @return The logged out user.
     * */
    public User register(User user){
        return userManager.register(user);
    }

    /**
     * Forwards the getDevices request to the DeviceManager.
     *
     * @return A list of devices.
     * */
    /* Device methods */
    public List<Device> getDevices(){
        Trace.out(Trace.Level.DEV,"conmgr.getdevices");
        return deviceManager.readAll(new Object());
    }

    /**
     * Forwards createDevice request to the DeviceManager.
     *
     * @param device The device to create.
     * @return The created device.
     * */
    public Device createDevice(Device device){
        return deviceManager.create(device);
    }

    /**
     * Forwards removeDevice request to the DeviceManager.
     *
     * @param device The device to remove.
     * @return True if the device was removed, false otherwise.
     * */
    public boolean removeDevice(Device device){
        return deviceManager.remove(device);
    }

    /**
     * Forwards getDevice request to the DeviceManager.
     *
     * @param deviceId The id of the device to update.
     * @return The device with the given id.
     * */
    public Device getDevice(Long deviceId){
        return deviceManager.read(deviceId);
    }

    // TODO: remove this method
    /* LogEntry methods */
    public List<LogEntry> getLogEntries(Device device){
        return getLogEntries(device, -1);
    }

    /**
     * Forwards getLogEntries request to the LogManager.
     *
     * @param device The device to get the log entries for.
     * @param limit The maximum number of log entries to return.
     *
     * @return A list of log entries.
     * */
    public List<LogEntry> getLogEntries(Device device, int limit){
        Trace.out(Trace.Level.DEV,"conmgr.getlogentries");
        Trace.out(Trace.Level.DEV, "Device: " + device.getName());
        return logManager.readAll(device, limit);
    }

    /**
     * Forwards removeLogEntry request to the LogManager.
     *
     * @param logEntry The log entry to remove.
     * @return True if the log entry was removed, false otherwise.
     * */
    public boolean removeLogEntry(LogEntry logEntry){
        return logManager.remove(logEntry);
    }

    /**
     * Forwards shareDevice request to the DeviceShareManager.
     *
     * @param deviceShare The device share to create.
     * @return The created device share.
     * */
    public DeviceShare shareDevice(DeviceShare deviceShare){
        return deviceShareManager.create(deviceShare);
    }

    /**
     * Forwards getDeviceShare request to the DeviceShareManager.
     *
     * @param device The device to get all device shares for.
     * @return A list of device shares.
     * */
    public List<DeviceShare> getDeviceShares(Device device){
        return deviceShareManager.readAll(device);
    }

    /**
     * Forwards getDeviceShares request to the DeviceShareManager .
     *
     * @return a list of all device shares a user has access to.
     * */
    public List<DeviceShare> getDeviceShares(){
        return deviceShareManager.readAll(null);
    }

    /**
     * Forwards removeDeviceShare request to the DeviceShareManager.
     *
     * @return True if the device share was removed, false otherwise.
     * */
    public boolean removeDeviceShare(DeviceShare deviceShare){
        return deviceShareManager.remove(deviceShare);
    }

    public boolean removeAllDeviceShares(Device device){
        return deviceShareManager.removeAll(device);
    }

    /**
     * Forwards updateDeviceShare request to the DeviceShareManager.
     *
     * @param deviceShare The device share to update.
     * @return The updated device share.
     * */
    public DeviceShare updateDeviceShare(DeviceShare deviceShare){
        return deviceShareManager.update(deviceShare);
    }

}
