package http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import model.User;
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
        }catch (JsonProcessingException e){e.printStackTrace();}


        HttpResponse<String> response = super.post();

        userParser.parse(response.body());

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
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }

        //userParser.parse(response.body());

        return null;
    }

    public static User handle(String s, Object o){
        return new User();
    }
}
