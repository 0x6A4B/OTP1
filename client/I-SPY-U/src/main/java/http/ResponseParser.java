package http;

import java.util.List;

public interface ResponseParser<T> {
    public T parse(String response);
    public List<T> parseList(String response);
}
