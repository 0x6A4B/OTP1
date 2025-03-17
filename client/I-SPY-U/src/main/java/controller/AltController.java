package controller;

import model.*;
import view.AltView;

import java.util.List;

public class AltController implements IAltController{
    Client client = new Client();
    AltView view;

    public AltController(AltView view) {
        this.view = view;
    }


    // Error message class?
    @Override
    public String login(String username, String password) {
        if (client.login(new User(username, password)) == null)
            return "ERROR in login";
        return "OK";
    }


    @Override
    public String logout() {
        return "";
    }

    @Override
    public Device addDevice(Device device) {
        return null;
    }


    @Override
    public List<Device> getDevices() {
        return client.getDevices(null); // Using logged in user
    }

    @Override
    public String deleteDevice(Device device) {
        return "";
    }

    @Override
    public Device getDevice(long deviceId) {
        return null;
    }

    @Override
    public List<LogEntry> getLogEntries(Device device) {
        return client.getLogEntries(device);
    }

    @Override
    public String deleteLogEntry(LogEntry logEntry) {
        return "";
    }

    @Override
    public List<DeviceShare> getDevicesShares(long deviceId) {
        return List.of();
    }

    @Override
    public List<DeviceShare> getDeviceShares() {
        return List.of();
    }

    @Override
    public String deleteDeviceShare(DeviceShare deviceShare) {
        return "";
    }

    @Override
    public DeviceShare addDeviceShare(Device device) {
        return null;
    }

    @Override
    public List<String> queryUsernames(String ngram) {
        return List.of();
    }
}
