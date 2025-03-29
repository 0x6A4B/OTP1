package controller;

import model.Device;
import model.DeviceShare;
import model.LogEntry;

import java.util.List;

public interface IAltController {
    String login(String username, String password);
    String logout();

    Device addDevice(Device device);
    List<Device> getDevices();
    String deleteDevice(Device device);
    Device getDevice(long deviceId);

    List<LogEntry> getLogEntries(Device device);
    String deleteLogEntry(LogEntry logEntry);

    List<DeviceShare> getDevicesShares(long deviceId);  /* Owner gets all shares for device */
    List<DeviceShare> getDeviceShares();    /* User gets all devices shared with them */
    String deleteDeviceShare(DeviceShare deviceShare);
    DeviceShare addDeviceShare(Device device);

    List<String> queryUsernames(String ngram);
    //List<>
}
