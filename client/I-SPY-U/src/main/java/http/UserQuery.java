package http;

import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import model.User;
import util.ConfigSingleton;
import util.Trace;

public class UserQuery extends HttpQuery {

    private UserParser userParser;

    public UserQuery(){
        super();
        userParser = new UserParser();
    }

    public User login(User user){
        Trace.out(Trace.Level.DEV,("userquery.login"));
        super.setEndpoint("/auth/login");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
            String json = ow.writeValueAsString(user);
            Trace.out(Trace.Level.DEV, "JSON: " + json);

            super.setBody(json);
        }catch (JsonProcessingException e){
            Trace.out(Trace.Level.ERR, "Error in parsing: " + e.getMessage());
        }


        try {
            HttpResponse<String> response = super.post();
            // TODO: FIX THIS QUICK fix to have user available in view
            //return userParser.parse(response.body());

            // TODO: Oh dear lord fix this
            User temp = userParser.parse(response.body());
            if (temp != null) {
                ConfigSingleton.getInstance().setUser(user);    // the UNGOOD, BAD and UGLY
                user.setId(temp.getId());
                return user;
            }
        }catch (Exception e){
            Trace.out(Trace.Level.ERR, "Error in logging in: "
                + e.getMessage());
        }

        return null;
    }

    public User register(User user){
        Trace.out(Trace.Level.DEV,("userquery.register"));
        super.setEndpoint("/auth/register");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
            String json = ow.writeValueAsString(user);
            Trace.out(Trace.Level.DEV, "JSON: " + json);

            super.setBody(json.toString());
            HttpResponse<String> response = super.post();
            // TODO: Fix response in backend => API responds with "success" instead of
            // user object
            if (response.statusCode() == 200) {
                return user;
            }
            return null;
            // END OF FIX
            //return userParser.parse(response.body());
        }catch(Exception e){
            Trace.out(Trace.Level.ERR, "Error in registering user or reading response");
        }


        return null;
    }
}
