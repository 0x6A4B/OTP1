package util;

import service.ConnectionManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigSingleton {
    private static ConfigSingleton instance = new ConfigSingleton();
    private String token;
    private String apiUrl;
    private Trace logger = new Trace();
    private boolean configLoaded = false;


    private ConfigSingleton(){
        Trace.setTraceLevel(Trace.Level.INFO);
        loadProperties();
    }

    public static ConfigSingleton getInstance(){ return instance; }

    public boolean configLoaded(){
        return configLoaded;
    }

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

    public Trace getLogger(){ return logger; }

    private void loadProperties(){
        try(InputStream is = ConnectionManager.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties prop = new Properties();
            prop.load(is);

            // Loading apiUrl
            apiUrl = prop.getProperty("apiUrl").replaceAll("\"", "");

            // Loading logger level config
            switch(prop.getProperty("loglevel").toLowerCase()){
                case "info" -> Trace.setTraceLevel(Trace.Level.INFO);
                case "warn" -> Trace.setTraceLevel(Trace.Level.WAR);
                case  "error" -> Trace.setTraceLevel(Trace.Level.ERR);
            }

            if (apiUrl.isEmpty()) {
                // eerror in reading apirul property
                //throw new Exception("Error in reading property: apiurl");
                System.out.println("Error in reading prop: apiurl");
            }
            System.out.println("Loaded config apiurl: " + apiUrl);
            //ConfigSingleton.getInstance().setApiUrl(apiUrl);

            token = prop.getProperty("token");
            configLoaded = true;
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

        if (apiUrl == "")
            System.err.println("ERROR, no apiUrl property not found!");
        System.out.printf("prop: %s\ntoken: %s\n", apiUrl, token);
    }
}
