package service;

import http.DeviceQuery;
import http.DeviceShareQuery;
import model.Device;
import model.DeviceShare;
import model.User;
import util.Trace;

import java.util.List;

public class DeviceShareManager implements IManager {
    private final DeviceShareQuery deviceShareQuery = new DeviceShareQuery();

    @Override
    public Object read(Object device) {
        // TODO: IS THIS USEFUL? IS IMPLEMENTED IN BACKEND
        return null;
    }

    @Override
    public DeviceShare update(Object deviceShare) {
        // TODO: UPDATE NOT IMPLEMENTED
        return deviceShareQuery.updateSharedDevice((DeviceShare) deviceShare);
    }

    @Override
    public DeviceShare create(Object deviceShare) {
        return deviceShareQuery.shareDevice((DeviceShare) deviceShare);
    }

    @Override
    public List<DeviceShare> readAll(Object o) {
        Trace.out(Trace.Level.DEV, "devmgr.readall");
        if (o == null)
            return deviceShareQuery.getSharedDevices();
        else
            return deviceShareQuery.getDeviceShares((Device) o);

    }

    @Override
    public boolean remove(Object device) {
        return deviceShareQuery.removeSharedDevice((Device) device);
    }
    // Why would we need and use this?
    // Why not iterate a list of devices to be removed and use normal remove device
    // TODO: API not supporting this
    @Override
    public boolean removeAll(Object o){
        return deviceShareQuery.removeAllSharedDevices(o);
    }

    // TODO: implement
    @Override
    public List readAll(Object o, int limit) {
        return List.of();
    }

}
