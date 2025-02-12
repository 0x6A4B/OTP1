package http;

public interface ResponseParser<T> {
    public T parse(String response);
}
