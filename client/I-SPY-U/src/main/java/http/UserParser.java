package http;

import util.ConfigSingleton;
import model.User;
import org.json.JSONObject;
import util.Trace;

import java.util.List;

public class UserParser implements ResponseParser {

    @Override
    public List<Object> parseList(String response){
        return null;
    }

    @Override
    public User parse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            String token = json.get("accessToken").toString();
            Trace.out(Trace.Level.DEV, "Got token: " + token);
            if (!token.equals("")) {
                ConfigSingleton.getInstance().setToken(token);
                return new User();
            }
        }catch (Exception e){
            Trace.out(Trace.Level.ERR, "Parsing error: "
                + e.getMessage());
        }
        return null;
    }
}
