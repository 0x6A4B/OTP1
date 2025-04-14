package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import model.Device;
import model.DeviceShare;
import util.Trace;

import java.net.http.HttpResponse;
import java.util.List;

public class DeviceShareQuery extends HttpQuery {
    private DeviceShareParser deviceShareParser;
    private static final String ENDPOINT = "/deviceshare";

    public DeviceShareQuery() {
        super();
        this.deviceShareParser = new DeviceShareParser();
    }

    public List<DeviceShare> getSharedDevices() {
        super.setEndpoint(ENDPOINT);
        try {
            HttpResponse<String> response = super.get();
            return deviceShareParser.parseList(response.body());
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Failed to fetch devices");
        }
        return List.of();
    }

    public List<DeviceShare> getDeviceShares(Device device) {
        super.setEndpoint(ENDPOINT + "/" + device.getId());
        try {
            HttpResponse<String> response = super.get();
            return deviceShareParser.parseList(response.body());
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Failed to fetch shares for device:" + device.getName());
        }
        return List.of();
    }

    public DeviceShare shareDevice(DeviceShare deviceShare) {
        super.setEndpoint(ENDPOINT);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            Trace.out(Trace.Level.DEV, "POST body: " + ow.writeValueAsString(deviceShare));
            super.setBody(ow.writeValueAsString(deviceShare));
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Failed to process json: " + e.getMessage());
        }
        try {
            HttpResponse<String> response = super.post();
            // TODO: response exception
            return deviceShareParser.parse(response.body());
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Failed to create device: " + e.getMessage());
        }
        return null;

    }

    public DeviceShare updateSharedDevice(DeviceShare deviceShare) {
        super.setEndpoint(ENDPOINT);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(deviceShare);
            Trace.out(Trace.Level.DEV, "POST body: " + json); //deviceShare.toString());
            super.setBody(json); //deviceShare.toString());
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Failed to process json: " + e.getMessage());
        }
        try {
            HttpResponse<String> response = super.patch();
            // TODO: response exception
            return deviceShareParser.parse(response.body());
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Failed to create device share: " + e.getMessage());
        }
        return null;

    }

    public boolean removeSharedDevice(DeviceShare deviceShare){
        Trace.out(Trace.Level.INFO, "Removing device: " + deviceShare.getId());
        super.setEndpoint(ENDPOINT + "/" + deviceShare.getId());
        try {
            return super.delete();
        }catch (Exception e) {
            // throw device not found exception
            Trace.out(Trace.Level.ERR, "Removal of device failed: " + e.getMessage());
        }
        return false;
    }

    // TODO: IMPLEMENT IN THE API
    public boolean removeAllSharedDevices(){
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

}
