package http;

import util.ConfigSingleton;
import util.Trace;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public abstract class HttpQuery {
    protected static String apiUrl = ConfigSingleton.getInstance().getApiUrl();
    protected static String token;
    protected static HttpClient httpClient = HttpClient.newHttpClient();

    protected  String endpoint;
    protected  String body;

    //public HttpQuery(String apiUrl) { this.apiUrl = apiUrl; }
    public HttpQuery(){}

    //public void setApiUrl(String apiUrl){ this.apiUrl = apiUrl; }
    public void setEndpoint(String endpoint){
        System.out.println("XX:" + apiUrl);
        this.endpoint = apiUrl + endpoint; }
    public void setBody(String body){ this.body = body; }

    public HttpResponse<String> post() throws Exception{

        Trace.out(Trace.Level.DEV, "httpquery.post");
        Trace.out(Trace.Level.DEV, "Endpoint: " + endpoint + "\ttoken: " + token);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Authorization", token == null ? "" : "Bearer " + token)
                .setHeader("Content-Type", "application/json")
                .build();

        Trace.out(Trace.Level.DEV, "Sending POST: " + request.headers());

        CompletableFuture<HttpResponse<String>> futureResponse
                = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = futureResponse.get();
            System.out.println("HttpQuery.Post response: " + response.statusCode());
            System.out.println("httpquery-post: " + response.body());
            // if not 200 || 201 then fuck
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }

    public HttpResponse<String> get() throws Exception{
        token = ConfigSingleton.getInstance().getToken();
        System.out.println("HttpQuery.Get: endpoint: " + endpoint + "\ttoken: " + token);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse
                = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = futureResponse.get();
            System.out.println("HttpQuery.Get response: " + response.statusCode());
            // if not 200 then fuck my life
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public HttpResponse<String> delete() throws Exception{
        token = ConfigSingleton.getInstance().getToken();
        Trace.out(Trace.Level.INFO, "HttpQuery.Delete: endpoint: " + endpoint + "\ttoken: " + token);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .DELETE()
                .header("Authorization", "Bearer " + token)
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse
                = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = futureResponse.get();
            Trace.out(Trace.Level.INFO,"HttpQuery.Get response: " + response.statusCode());
            //if not 204 || 200 then fucked are we
            // throw new Exception
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}

