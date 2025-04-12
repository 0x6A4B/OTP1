package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import model.Device;
import util.Trace;

import java.net.http.HttpResponse;
import java.util.List;

public class DeviceQuery extends HttpQuery {
    private DeviceParser deviceParser;
    private final String endpoint = "/device";

    public DeviceQuery() {
        super();
        this.deviceParser = new DeviceParser();
    }

    public List<Device> getDevices() {
        Trace.out(Trace.Level.DEV, ("devq.getdevices"));
        super.setEndpoint("/device");
        try {
            HttpResponse<String> response = super.get();
            return deviceParser.parseList(response.body());
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Failed to fetch devices");
        }
        return null;
    }

    public Device createDevice(Device device){
        super.setEndpoint(endpoint);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            //String body = ow.writeValueAsString(device);
            Trace.out(Trace.Level.DEV, "POST body: " + device.toString());
            super.setBody(device.toString());
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Failed to process json");
        }
        try {
            HttpResponse<String> response = super.post();
            // TODO: response exception
            return deviceParser.parse(response.body());
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Failed to create device");
        }
        return null;

    }


    public boolean removeDevice(Device device){
        Trace.out(Trace.Level.INFO, "Removing device: " + device);
        super.setEndpoint(endpoint + "/" + device.getId());
        try {
            return super.delete();
        }catch (Exception e) {
            // throw device not found exception
            Trace.out(Trace.Level.ERR, "Removal of device failed");
        }
        return false;
    }

    // TODO: IMPLEMENT IN THE API
    public boolean removeAllDevices(Object o){
        super.setEndpoint(endpoint + "/deleteall");
        try {
            super.delete();
            return true;
        }catch (Exception e) {
            // throw device not found exception
            Trace.out(Trace.Level.ERR, "Removal of all devices failed: " + e.getMessage());
        }
        return false;
    }

    public Device getDevice(Long deviceId){
        super.setEndpoint(endpoint + "/" + deviceId);
        try {
            HttpResponse<String> response = super.get();
            // TODO: response exception
            return deviceParser.parse(response.body());
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "GET device: " + deviceId + " failed: " + e.getMessage());
            return null;
        }
    }

}
