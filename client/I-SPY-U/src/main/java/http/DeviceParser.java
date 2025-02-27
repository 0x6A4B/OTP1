package http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.ConfigSingleton;
import model.Device;

import java.util.List;

public class DeviceParser implements ResponseParser{
    private final String token = ConfigSingleton.getInstance().getToken();
/*
    public ArrayList<Device> parseDevices(Object deviceJSON) {

        // Return a list of devices created from the JSON object(s)

        return new ArrayList<>();
    }
*/
    public Object parse(String response) {


        try {
            //JSONObject json = new JSONObject(response);
            //token = json.get("accessToken").toString();
            //System.out.println(token);
            //if (!token.equals("")) {
            //    ConfigSingleton.getInstance().setToken(token);
            //    return new Device();
            //}
        }catch (Exception e){ e.printStackTrace(); }
        return null;
    }

    public List<Device> parseList(String response) {
        System.out.println("devparser.parselist");
        try {
            List<Device> devices = new ObjectMapper().readValue(response, new TypeReference<List<Device>>() {});
            devices.forEach(d -> System.out.println(d.getName()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        /*
        try {
            String json = ow.writeValueAsString(user);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        try {
            JSONObject json = new JSONObject(response);
//            token = json.get("accessToken").toString();
//            System.out.println(token);
//            if (!token.equals("")) {
//                ConfigSingleton.getInstance().setToken(token);
//                return new Device();


        }catch (Exception e){ e.printStackTrace(); }

         */
        return null;
    }

}
