package http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.ConfigSingleton;
import model.Device;
import util.Trace;

import java.util.List;

public class DeviceParser implements ResponseParser{
    private final String token = ConfigSingleton.getInstance().getToken();
/*
    public ArrayList<Device> parseDevices(Object deviceJSON) {

        // Return a list of devices created from the JSON object(s)

        return new ArrayList<>();
    }
*/
    public Device parse(String response) {


        try {
            Device device = new ObjectMapper().readValue(response, new TypeReference<Device>() {});
            return device;
        } catch (Exception e){ e.printStackTrace(); }
            return null;
        }

    public List<Device> parseList(String response) {
        Trace.out(Trace.Level.DEV, "devparser.parselist");
        try {
            List<Device> devices = new ObjectMapper().readValue(response, new TypeReference<List<Device>>() {});
            devices.forEach(d -> System.out.println(d.getName()));
            return devices;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
