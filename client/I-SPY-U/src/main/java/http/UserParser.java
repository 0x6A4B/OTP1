package http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Device;
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
        Trace.out(Trace.Level.DEV, "Response: " + response);

        if (response.contains("accessToken"))
            try {
                JSONObject json = new JSONObject(response);
                String token = json.get("accessToken").toString();
                Trace.out(Trace.Level.DEV, "Got token: " + token);
                if (!token.equals("")) {
                    ConfigSingleton.getInstance().setToken(token);
                    return new User();  // TODO: This API doesn't return user but just the token
                }
            }catch (Exception e){
                Trace.out(Trace.Level.ERR, "Parsing error: "
                    + e.getMessage());
            }
        else if (response.contains("id"))
            try{
                return new ObjectMapper().readValue(response, new TypeReference<User>() {});
            } catch (Exception e) {
                Trace.out(Trace.Level.ERR, "Parsing error: "
                    + e.getMessage());
            }

        return null;
    }
}
