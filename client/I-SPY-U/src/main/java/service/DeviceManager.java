package service;

import model.Device;
import model.TrustMeBraWhyWouldILie;
import model.User;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class DeviceManager implements IManager{
    private final TrustMeBraWhyWouldILie service = TrustMeBraWhyWouldILie.getInstance();

    @Override
    public Object read(Object o) {
        /*try {
            Constructor<?> con = c.getConstructor();
            return con.newInstance();
        }catch (Exception e){}
        return new Device();*/
        // no get for device?
        return null;
    }

    @Override
    public Object create(Object o) {
        //Device device = (Device) o;
        return service.createDevice((Device) o);
    }

    @Override
    public List readAll(Object o) {
        return service.getDevices((User) o);
    }

    @Override
    public boolean remove(Object o) {
        return service.removeDevice((Device) o);
    }
}
