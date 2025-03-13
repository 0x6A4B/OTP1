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
    private final String endpoint = "/deviceshare";

    public DeviceShareQuery() {
        super();
        this.deviceShareParser = new DeviceShareParser();
    }

    public List<DeviceShare> getSharedDevices() {
        System.out.println("devq.getdevices");
        super.setEndpoint(endpoint);
        try{
            HttpResponse<String> response = super.get();
            return deviceShareParser.parseList(response.body());
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Failed to fetch devices");
        }
        return null;
    }

    public List<DeviceShare> getDeviceShares(Device device) {
        System.out.println("devq.getdevicessahres for device:" + device.getName());
        super.setEndpoint(endpoint + "/" + device.getId());
        try{
            HttpResponse<String> response = super.get();
            return deviceShareParser.parseList(response.body());
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Failed to fetch shares for device:" + device.getName());
        }
        return null;
    }

    public DeviceShare shareDevice(DeviceShare deviceShare) {
        super.setEndpoint(endpoint);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            //String body = ow.writeValueAsString(device);
            Trace.out(Trace.Level.DEV, "POST body: " + deviceShare.toString());
            super.setBody(deviceShare.toString());
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Failed to process json");
        }
        try{
            HttpResponse<String> response = super.post();
            // TODO: response exception
            return deviceShareParser.parse(response.body());
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Failed to create device");
        }
        return null;

    }

    public DeviceShare updateSharedDevice(DeviceShare deviceShare) {
        super.setEndpoint(endpoint);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            //String body = ow.writeValueAsString(device);
            Trace.out(Trace.Level.DEV, "POST body: " + deviceShare.toString());
            super.setBody(deviceShare.toString());
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Failed to process json");
        }
        try{
            HttpResponse<String> response = super.patch();
            // TODO: response exception
            return deviceShareParser.parse(response.body());
        } catch (Exception e){
            Trace.out(Trace.Level.ERR, "Failed to create device");
        }
        return null;

    }

    public boolean removeSharedDevice(Device device){
        Trace.out(Trace.Level.INFO, "Removing device: " + device);
        super.setEndpoint(endpoint + "/" + device.getId());
        try {
            super.delete();
            return true;
        }catch (Exception e) {
            // throw device not found exception
            Trace.out(Trace.Level.ERR, "Removal of device failed");
        }
        return false;
    }

    // TODO: IMPLEMENT IN THE API
    public boolean removeAllSharedDevices(Object o){
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

}
