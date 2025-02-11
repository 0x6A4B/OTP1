package service;

import model.Device;
import model.LogEntry;
import model.TrustMeBraWhyWouldILie;

import java.util.ArrayList;
import java.util.List;

public class LogManager implements IManager{
    private final TrustMeBraWhyWouldILie service = TrustMeBraWhyWouldILie.getInstance();

    @Override
    public Object read(Object o) {
        return service.getLogEntries((Device) o);
    }

    @Override
    public Object create(Object o) {
        //return service.createLogEntry((LogEntry) o);
        return null; // can't create logentries, sensors create them
    }

    @Override
    public List readAll(Object o) {
        return service.getLogEntries((Device) o);
    }

    @Override
    public boolean remove(Object o) {
        return service.removeLogEntry((LogEntry) o);
    }
}
