package http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.ConfigSingleton;
import model.Device;
import util.Trace;

import java.util.List;

public class DeviceParser implements ResponseParser {
    private final String token = ConfigSingleton.getInstance().getToken();


    public Device parse(String response) {
        try {
            return new ObjectMapper().readValue(response, new TypeReference<Device>() {});
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Error in parsing device: " + e.getMessage());
        }
        return null;
    }

    public List<Device> parseList(String response) {
        Trace.out(Trace.Level.DEV, "devparser.parselist");
        try {
            List<Device> devices = new ObjectMapper().readValue(response, new TypeReference<List<Device>>() {});
            devices.forEach(d -> Trace.out(Trace.Level.INFO, (d.getName())));
            return devices;
        } catch (JsonProcessingException e) {
            Trace.out(Trace.Level.ERR, "Error in parsing device list: " + e.getMessage());
        }
        return null;
    }

}
