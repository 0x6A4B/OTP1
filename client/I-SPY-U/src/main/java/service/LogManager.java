package service;

import http.LogQuery;
import model.Device;
import model.LogEntry;
import model.TrustMeBraWhyWouldILie;

import java.util.ArrayList;
import java.util.List;

public class LogManager implements IManager{
    private final TrustMeBraWhyWouldILie service = TrustMeBraWhyWouldILie.getInstance();
    private final LogQuery logQuery = new LogQuery();
    @Override
    public Object read(Object o) {
        return LogQuery.handle("READ", o);
    }

    @Override
    public Object create(Object o) {
        //return service.createLogEntry((LogEntry) o);
        return null; // can't create logentries, sensors create them
    }

    @Override
    public Object update(Object o) {
        return null; // can't update logentries
    }

    @Override
    public List readAll(Object o) {
        return LogQuery.handle("READALL", o);
    }

    @Override
    public boolean remove(Object o) {
        return LogQuery.handle("REMOVE", o);
    }
}
