package http;

import util.ConfigSingleton;
import model.User;
import org.json.JSONObject;

import java.util.List;

public class UserParser implements ResponseParser {

    @Override
    public List<Object> parseList(String response){
        return null;
    }

    @Override
    public Object parse(String response) {

        String token;

        try {
            JSONObject json = new JSONObject(response);
            token = json.get("accessToken").toString();
            System.out.println(token);
            if (!token.equals("")) {
                ConfigSingleton.getInstance().setToken(token);
                return new User();
            }
        }catch (Exception e){ e.printStackTrace(); }
        return null;
    }
}
