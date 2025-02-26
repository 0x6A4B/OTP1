package http;

import config.ConfigSingleton;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public abstract class HttpQuery {
    protected static String apiUrl = ConfigSingleton.getInstance().getApiUrl();
    protected static String token = "";
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

    public HttpResponse<String> post(){

        System.out.println("httpquery.post");
        System.out.println("Endpoint: " + endpoint);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Authorization", "Bearer " + token != null ? token : "")
                .setHeader("Content-Type", "application/json")
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse
                = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = futureResponse.get();
            System.out.println("HttpQuery.Post response: " + response.statusCode());
//            if (response.statusCode() == 400)
//                System.out.println("RESPONSE: " + );
            System.out.println("httpquery-post: " + response.body());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }

    public HttpResponse<String> get(){
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
            return response;
            //System.out.println(response.body());
            /*try {
                JSONObject json = new JSONObject(response.body());
                //token = json.get("accessToken").toString();

            }catch (Exception e){ e.printStackTrace(); }
*/
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}

