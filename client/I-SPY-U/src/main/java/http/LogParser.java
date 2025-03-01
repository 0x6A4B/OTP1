package http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.LogEntry;
import util.Trace;
import java.util.List;

public class LogParser implements ResponseParser{
    private final ObjectMapper mapper = new ObjectMapper();

    public List<LogEntry> parseList(String response) {
        Trace.out(Trace.Level.DEV,"logparser.parselist");
        try {
            List<LogEntry> logs = mapper.readValue(response,
                    new TypeReference<List<LogEntry>>() {});
            logs.forEach(l -> Trace.out(Trace.Level.INFO,
                            " Logkey: " + l.getLogkey() +
                            " Value: " + l.getValue() +
                            " Date: " + l.getDate())
                            );

            return logs;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LogEntry parse(String response) {
        Trace.out(Trace.Level.DEV,"logparser.parse");
        try {
            return mapper.readValue(response, LogEntry.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}