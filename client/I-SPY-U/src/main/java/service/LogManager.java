package service;

import http.LogQuery;
import model.Device;
import model.LogEntry;
import util.Trace;

import java.util.List;

public class LogManager implements IManager{
    private final LogQuery logQuery = new LogQuery();

    @Override
    public Object read(Object o) {
        // TODO: DO WE NEED THIS?
        return null;
    }

    @Override
    public Object create(Object o) {
        return null; // can't create logentries, sensors create them
    }

    @Override
    public Object update(Object o) {
        return null; // can't update logentries
    }

    @Override
    public List<LogEntry> readAll(Object o) {
        Trace.out(Trace.Level.DEV, ("logmgr.readall"));
        return logQuery.getLogsByDevice((Device) o);
    }

    @Override
    public boolean remove(Object o) {
        return logQuery.removeLogEntry((LogEntry) o);
    }

    @Override
    public boolean removeAll(Object o){
        // TODO: implement removing all logs
        return false;
    }
}
