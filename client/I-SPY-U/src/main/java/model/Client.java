package model;

import service.*;
import util.ConfigSingleton;
import util.Trace;
import java.util.List;

/**
 * Client class that serves as the main interface for the application to interact with backend services.
 * Provides methods for managing devices, device shares, log entries, and user authentication.
 * Uses ConnectionManager for all backend communications.
 */

public class Client {
    private final ConnectionManager connectionManager = new ConnectionManager();
    private boolean rememberUser;


    public Client(){
        while(!ConfigSingleton.getInstance().configLoaded())
            Trace.out(Trace.Level.DEV, "Loading properties...");

        Trace.out(Trace.Level.INFO, "Client instantiated");
    }

    /**
     * Retrieves the list of devices associated with a user.
     *
     * @param user The user whose devices to retrieve.
     * @return List of devices belonging to the user.
     */
    public List<Device> getDevices(User user){
        return connectionManager.getDevices();
    }

    /**
     * Adds a new device to the system.
     *
     * @param device The device to add.
     * @return The created device.
     */
    public Device addDevice(Device device){
        return connectionManager.createDevice(device);
    }

    /**
     * Updates an existing device in the system.
     *
     * @param device The device to update.
     * @return The updated device.
     */
    public boolean removeDevice(Device device){
        return connectionManager.removeDevice(device);
    }

    /**
     * Retrieves a divice by its UUID.
     *
     * @param deviceId The UUID of the device to retrieve.
     * @return the device object with the specified UUID.
     */
    public Device getDevice(Long deviceId){
        return connectionManager.getDevice(deviceId);
    }

    /**
     * Allows user to share their own device's details to other users.
     *
     * @param deviceShare object containing device, user, privilege and description.
     * @return the created DeviceShare object.
     * */
    public DeviceShare shareDevice(DeviceShare deviceShare) {
        return connectionManager.shareDevice(deviceShare);
    }
    public boolean removeDeviceShare(DeviceShare deviceShare){
        return connectionManager.removeDeviceShare(deviceShare);
    }
    /**
     * Retrieves a list of shares for a specific device owned by the user.
     *
     * @param device the device object to retrieve shares for.
     * @return a list of DeviceShare objects associated with the device.
     * */
    /* Only owner can get all shares they have made */
    public List<DeviceShare> getDeviceShares(Device device){
        return connectionManager.getDeviceShares(device);
    }

    /* All devices shared with you */
    /**
     * Retrieves a list of all devices shared with the user.
     *
     * @return a list of DeviceShare objects associated with the user.
     * */
    public List<DeviceShare> getDeviceShares(){
        return connectionManager.getDeviceShares();
    }

    /**
     * Updates the details of a device share.
     *
     * @param deviceShare the DeviceShare object to update.
     * @return the updated DeviceShare object.
     * */
    public DeviceShare updateDeviceShare(DeviceShare deviceShare){
        return connectionManager.updateDeviceShare(deviceShare);
    }

    /* LOG */
    // TODO: Deprecating this...??
    /**
     * Gets the log entries for a specific device associated with the user without limit on the number of entries.
     *
     * @param device the device object to retrieve log entries for.
     * @return a list of LogEntry objects associated with the device.
     * */
    public List<LogEntry> getLogEntries(Device device){
        return getLogEntries(device, -1);
    }

    /**
     * Gets the log entries for a specific device associated with the user with a limit on the number of entries.
     *
     * @param device the device object to retrieve log entries for.
     * @param limit the maximum number of log entries to retrieve.
     * @return a list of LogEntry objects associated with the device.
     * */
    public List<LogEntry> getLogEntries(Device device, int limit){
        return connectionManager.getLogEntries(device, limit);
    }
    // TODO: implement log entry removal
    public boolean removeLogEntry(LogEntry logEntry){
        return connectionManager.removeLogEntry(logEntry);
    }


    /**
     * Logs in a user to the system.
     *
     * @param user The user to log in.
     * @return The logged-in user object.
     */
    public User login(User user){
        Trace.out(Trace.Level.DEV, "client.login");

        User loggedUser = connectionManager.login(user);
        if (rememberUser)
            ConfigSingleton.getInstance().saveToken();
        return loggedUser;
    }

    /**
     * Checks if the user is remembered for future logins.
     *
     * @return true if the user is remembered, false otherwise.
     */
    public void setRememberUser(boolean rememberUser){
        this.rememberUser = rememberUser;
    }

    /**
     * Registers a new user in the system.
     *
     * @param user The user to register.
     * @return The registered user object.
     */
    public User register(User user){
        return connectionManager.register(user);
    }

    /**
     * Logs out the current user from the system.
     */
    public void logout(){
        Trace.out(Trace.Level.INFO, "User logged out");
        ConfigSingleton.getInstance().setToken("");
        ConfigSingleton.getInstance().saveToken();
    }
}
