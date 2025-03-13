package http;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.DeviceShare;
import util.Trace;


import java.util.List;

public class DeviceShareParser implements ResponseParser {
    public DeviceShare parse(String response) {
        try {
            return new ObjectMapper().readValue(response, new TypeReference<DeviceShare>() {
            });
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Error in parsing deviceShare: " + e.getMessage());
        }
        return null;
    }

    public List<DeviceShare> parseList(String response) {
        try {
            return new ObjectMapper().readValue(response, new TypeReference<List<DeviceShare>>() {});
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Error in parsing deviceShare list: " + e.getMessage());
        }
        return null;

    }
}
