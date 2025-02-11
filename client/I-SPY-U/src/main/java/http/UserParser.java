package http;

import model.User;

public class UserParser implements ResponseParser{
    @Override
    public Object parse(String response) {
        return new User();
    }
}
