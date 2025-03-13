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

    protected String endpoint;
    protected String body;

    //public HttpQuery(String apiUrl) { this.apiUrl = apiUrl; }
    public HttpQuery() {
    }

    //public void setApiUrl(String apiUrl){ this.apiUrl = apiUrl; }

    public void setEndpoint(String endpoint){ this.endpoint = apiUrl + endpoint; }
    public void setBody(String body){ this.body = body; }


    public HttpResponse<String> post() throws Exception {

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
            Trace.out(Trace.Level.INFO, "HTTP Response status code: " + response.statusCode());
            Trace.out(Trace.Level.DEV,  ("res body" + response.body()));
            // if not 200 || 201 then fuck
            return response;
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "POST request failed" + e.getMessage());
        }
        return null;
    }

    public HttpResponse<String> get() throws Exception {
        token = ConfigSingleton.getInstance().getToken();
        Trace.out(Trace.Level.DEV,("HttpQuery.Get: endpoint: " + endpoint + "\ttoken: " + token));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse
                = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = futureResponse.get();
            Trace.out(Trace.Level.INFO, "HTTP Response status code: " + response.statusCode());            // if not 200 then fuck my life
            return response;
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "GET request failed" + e.getMessage());
        }


        return null;
    }

    public HttpResponse<String> delete() throws Exception {
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
            Trace.out(Trace.Level.INFO, "HttpQuery.Delete response: " + response.statusCode());
            //if not 204 || 200 then fucked are we
            // throw new Exception
            return response;
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "DELETE request failed" + e.getMessage());
        }


        return null;
    }

    public HttpResponse<String> patch() throws Exception {
        token = ConfigSingleton.getInstance().getToken();
        Trace.out(Trace.Level.INFO, "HttpQuery.Patch: endpoint: " + endpoint + "\ttoken: " + token);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse
                = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = futureResponse.get();
            Trace.out(Trace.Level.INFO, "HttpQuery.Patch response: " + response.statusCode());
            //if not 204 || 200 then fucked are we
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

