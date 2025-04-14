package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import model.Device;
import util.Trace;

import java.net.http.HttpResponse;
import java.util.List;

public class DeviceQuery extends HttpQuery {
    private DeviceParser deviceParser;
    private static final String ENDPOINT = "/device";

    public DeviceQuery() {
        super();
        this.deviceParser = new DeviceParser();
    }

    public List<Device> getDevices() {
        Trace.out(Trace.Level.DEV, ("devq.getdevices"));
        super.setEndpoint(ENDPOINT);
        try {
            HttpResponse<String> response = super.get();
            return deviceParser.parseList(response.body());
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Failed to fetch devices");
        }
        return List.of();
    }

    public Device createDevice(Device device){
        super.setEndpoint(ENDPOINT);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            Trace.out(Trace.Level.DEV, "POST body: " + ow.writeValueAsString(device));
            super.setBody(ow.writeValueAsString(device));
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
        super.setEndpoint(ENDPOINT + "/" + device.getId());
        try {
            return super.delete();
        }catch (Exception e) {
            // throw device not found exception
            Trace.out(Trace.Level.ERR, "Removal of device failed");
        }
        return false;
    }

    // TODO: IMPLEMENT IN THE API
    public boolean removeAllDevices(){
        super.setEndpoint(ENDPOINT + "/deleteall");
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
        super.setEndpoint(ENDPOINT + "/" + deviceId);
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
