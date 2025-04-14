package http;

import model.LogEntry;
import model.Device;
import util.Trace;
import java.net.http.HttpResponse;
import java.util.List;

public class LogQuery extends HttpQuery {
    private LogParser logParser;
    private String endpoint = "/log";

    public LogQuery() {
        super();
        this.logParser = new LogParser();
    }

    public List<LogEntry> getLogsByDevice(Device device) {
        return getLogsByDevice(device, -1);
    }

    public List<LogEntry> getLogsByDevice(Device device, int limit) {
        Trace.out(Trace.Level.DEV, "logq.getlogsbydevice: " + device.getId());

        super.setEndpoint(endpoint + "/bydevice/" + device.getId() + (limit > 0 ? "?limit=" + limit : ""));

        try {
            HttpResponse<String> response = super.get();
            Trace.out(Trace.Level.INFO, "Device: " + device.getName());
            return logParser.parseList(response.body())
                    .stream()
                    .map(logentry -> logentry.setDevice(device))
                    .toList();
        }catch (Exception e){
            Trace.out(Trace.Level.ERR, "Error in fetching logs: " + e.getMessage());
        }
        return List.of();
    }

    public LogEntry getLogEntry(Long id) {
        Trace.out(Trace.Level.DEV,"logq.getlogentry: " + id);

        super.setEndpoint(endpoint + "/" + id);
        try {
            HttpResponse<String> response = super.get();
            return logParser.parse(response.body());
        }catch (Exception e){
            Trace.out(Trace.Level.ERR, "Error in reading log entry: " + e.getMessage());
        }
        return null;
    }

    public boolean removeLogEntry(LogEntry logEntry){
        super.setEndpoint(endpoint + "/" + logEntry.getId());

        try {
            return super.delete();
        }catch (Exception e){
            Trace.out(Trace.Level.ERR, "Error in reading log entry: " + e.getMessage());
        }
        return false;
    }


}
