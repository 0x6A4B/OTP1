package config;

import service.ConnectionManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigSingleton {
    private static ConfigSingleton instance = new ConfigSingleton();
    private String token;
    private String apiUrl;

    private ConfigSingleton(){ loadProperties(); }
    public static ConfigSingleton getInstance(){ return instance; }

    public void setToken(String token){
        System.out.println("TOKEN has been set: " + token);
        this.token = token; }
    public void setApiUrl(String apiUrl){
        System.out.println("APIRUL has been set: " + apiUrl);
        this.apiUrl = apiUrl; }

    public String getToken(){ return token; }
    public String getApiUrl(){
        System.out.println("API URL has been called: " + apiUrl);
        //if (apiUrl.isEmpty())
        //    loadProperties();
        return apiUrl;
    }

    private void loadProperties(){
        try(InputStream is = ConnectionManager.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties prop = new Properties();
            prop.load(is);
            apiUrl = prop.getProperty("apiUrl").replaceAll("\"", "");
            if (apiUrl.isEmpty()) {
                // eerror in reading apirul property
                //throw new Exception("Error in reading property: apiurl");
                System.out.println("Error in reading prop: apiurl");
            }
            System.out.println("Loaded config apiurl: " + apiUrl);
            //ConfigSingleton.getInstance().setApiUrl(apiUrl);

            token = prop.getProperty("token");
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

        if (apiUrl == "")
            System.err.println("ERROR, no apiUrl property not found!");
        System.out.printf("prop: %s\ntoken: %s\n", apiUrl, token);
    }
}
