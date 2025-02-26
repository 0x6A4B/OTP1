package http;

import model.Device;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DeviceQuery extends HttpQuery {
    private DeviceParser deviceParser;
    public DeviceQuery() {
        super();
        this.deviceParser = new DeviceParser();
    }

    public List<Device> getDevices(){
        System.out.println("devq.getdevices");
        super.setEndpoint("/device");
        HttpResponse<String> response = super.get();
        return deviceParser.parseList(response.body());
    }

    public static Object handle(String s, Object o){ return new Object(); }


}
