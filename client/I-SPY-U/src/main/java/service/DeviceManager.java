package service;

import http.DeviceQuery;
import model.Device;
import util.Trace;
import java.util.List;

public class DeviceManager implements IManager {
    private final DeviceQuery deviceQuery = new DeviceQuery();

    @Override
    public Object read(Object device) {
        // TODO: IS THIS USEFUL? IS IMPLEMENTED IN BACKEND
        return null;
    }

    @Override
    public Object update(Object device) {
        // TODO: UPDATE NOT IMPLEMENTED
        return null;
    }


    @Override
    public Device create(Object device) {
        return deviceQuery.createDevice((Device) device);
    }

    @Override
    public List<Device> readAll(Object o) {
        Trace.out(Trace.Level.DEV, "devmgr.readall");
        return deviceQuery.getDevices();
    }

    @Override
    public boolean remove(Object device) {
        return deviceQuery.removeDevice((Device) device);
    }

    // Why would we need and use this?
    // Why not iterate a list of devices to be removed and use normal remove device
    // TODO: API not supporting this
    @Override
    public boolean removeAll(Object o){
        return deviceQuery.removeAllDevices(o);
    }


    // TODO: implement
    @Override
    public List readAll(Object o, int limit) {
        return List.of();
    }
}
