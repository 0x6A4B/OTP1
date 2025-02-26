package http;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public /*abstract*/ class HttpQuery {
    private String apiUrl;
    private String token = "";
    private HttpClient httpClient = HttpClient.newHttpClient();

    public HttpQuery(String apiUrl) { this.apiUrl = apiUrl; }

    //public void setApiUrl(String apiUrl){ this.apiUrl = apiUrl; }

    public boolean login(){
        String endpoint = apiUrl + "/auth/login";
        String body = "{\"username\":\"wasdi\", \"password\":\"wasdi\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .setHeader("Content-Type", "application/json")
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse
                = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = futureResponse.get();
            System.out.println(response.body());
            try {
                JSONObject json = new JSONObject(response.body());
                token = json.get("accessToken").toString();
                System.out.println(token);
                if (!token.equals(""))
                    return true;
            }catch (Exception e){ e.printStackTrace(); }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;


    }

    public void getDev(){
        String endpoint = apiUrl + "/device";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse
                = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = futureResponse.get();
            System.out.println(response.body());
            /*try {
                JSONObject json = new JSONObject(response.body());
                //token = json.get("accessToken").toString();

            }catch (Exception e){ e.printStackTrace(); }
*/
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}

