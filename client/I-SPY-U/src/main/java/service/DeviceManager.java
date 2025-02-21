package service;

import http.DeviceParser;
import http.DeviceQuery;
import model.Device;
import model.TrustMeBraWhyWouldILie;
import model.User;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class DeviceManager implements IManager {
    private final TrustMeBraWhyWouldILie service = TrustMeBraWhyWouldILie.getInstance();
    private final DeviceQuery deviceQuery = new DeviceQuery();
    private ArrayList<Device> devices = new ArrayList<>();

    @Override
    public Object read(Object device) {

        /*try {
            Constructor<?> con = c.getConstructor();
            return con.newInstance();
        }catch (Exception e){}
        return new Device();*/
        // no get for device?
        return null;
    }

    @Override
    public Object update(Object device) {
        return deviceQuery.handle("UPDATE", device);
    }

    public void initialDeviceList() {
        Object alldevices = deviceQuery.handle("getDevices", devices);

        ArrayList<Device> devices = (ArrayList<Device>) alldevices;
    }

    @Override
    public Object create(Object device) {
        Device newDevice = deviceQuery.handle("CREATE", device);
        devices.add(newDevice);
        return newDevice;
    }

    @Override
    public List readAll(Object user) {
        if (devices.isEmpty()) {
            initialDeviceList();
        }
        return devices;
    }

    @Override
    public boolean remove(Object device) {
        Device deviceToRemove = (Device) device;
        boolean removed = deviceQuery.handle("REMOVE", deviceToRemove);
        if (removed) {
            devices.remove(deviceToRemove);
        }
        return removed;
    }
}
