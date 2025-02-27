package http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import model.User;
import util.Trace;
import java.net.http.HttpResponse;

public class UserQuery extends HttpQuery {

    private UserParser userParser;

    public UserQuery(){
        super();
        userParser = new UserParser();
    }

    public User login(User user){
        System.out.println("userquery.login");
        super.setEndpoint("/auth/login");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
            String json = ow.writeValueAsString(user);
            super.setBody(json);
        }catch (JsonProcessingException e){
            Trace.out(Trace.Level.ERR, "Error in parsing: " + e.getMessage());
            e.printStackTrace();
        }


        try {
            HttpResponse<String> response = super.post();
            return userParser.parse(response.body());
        }catch (Exception e){
            Trace.out(Trace.Level.ERR, "Error in logging in: "
                + e.getMessage());
        }

        return null;
    }

    public User register(User user){
        System.out.println("userquery.register");
        super.setEndpoint(apiUrl + "/auth/register");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
            String json = ow.writeValueAsString(user);
            super.setBody(json.toString());
            HttpResponse<String> response = super.post();
        }catch(Exception e){
            Trace.out(Trace.Level.ERR, "Error in registering user or reading response");
            e.printStackTrace();
        }


        return null;
    }
}
