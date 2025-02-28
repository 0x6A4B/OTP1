package util;

import model.User;
import service.ConnectionManager;

import java.io.*;
import java.util.Properties;

public class ConfigSingleton {
    private static ConfigSingleton instance = new ConfigSingleton();
    private String token = "";
    private String apiUrl;
    private boolean configLoaded = false;
    private final String configFile = "app.properties";
    private final String userConfigFile = ConfigSingleton.class.getResource("..").getPath() + "app.cfg";

    // TODO: GET a better solution for UI to track user
    private User user;

    public void setUser(User user){ this.user = user; }
    public User getUser(){ return user; }

    // END

    private ConfigSingleton(){
        Trace.setTraceLevel(Trace.Level.INFO);
        loadProperties();
    }

    public static ConfigSingleton getInstance(){ return instance; }

    public boolean configLoaded(){
        return configLoaded;
    }

    public void setToken(String token){
        Trace.out(Trace.Level.DEV,"TOKEN has been set: " + token);
        this.token = token; }
    public void setApiUrl(String apiUrl){
        Trace.out(Trace.Level.DEV,"APIRUL has been set: " + apiUrl);
        this.apiUrl = apiUrl; }

    public String getToken(){ return token; }
    public String getApiUrl(){
        Trace.out(Trace.Level.DEV,"API URL has been called: " + apiUrl);
        return apiUrl;
    }

    private void loadProperties(){
        try(InputStream is = ConnectionManager.class.getClassLoader().getResourceAsStream(configFile)) {
            Properties prop = new Properties();
            prop.load(is);

            // Loading apiUrl
            apiUrl = prop.getProperty("apiUrl");

            // Loading logger level config
            switch(prop.getProperty("loglevel").toLowerCase()){
                case "info" -> Trace.setTraceLevel(Trace.Level.INFO);
                case "warn" -> Trace.setTraceLevel(Trace.Level.WAR);
                case  "error" -> Trace.setTraceLevel(Trace.Level.ERR);
                case "dev" -> Trace.setTraceLevel(Trace.Level.DEV);
            }

            if (apiUrl.isEmpty()) {
                // eerror in reading apirul property
                //throw new Exception("Error in reading property: apiurl");
                Trace.out(Trace.Level.ERR,"Error in reading prop: apiurl");
            }
            Trace.out(Trace.Level.DEV,"Loaded config apiurl: " + apiUrl);
            //token = prop.getProperty("token");
            loadToken();
            configLoaded = true;
        }catch (IOException e){
            Trace.out(Trace.Level.ERR, "Error in loading configuration" + e.getMessage());
        }

        if (apiUrl == "")
            Trace.out(Trace.Level.ERR,"ERROR, no apiUrl property not found!");
        Trace.out(Trace.Level.DEV,"prop: " + apiUrl + "\ntoken: " + token);
    }

    // Remember logged in user
    public void saveToken(){
        try{
            Properties prop = new Properties();
            prop.setProperty("token", token);
            Trace.out(Trace.Level.DEV, "Saving to file: " + userConfigFile);
            prop.store(new FileOutputStream(userConfigFile), null);

        }catch (Exception e){
            Trace.out(Trace.Level.ERR, "Error in saving user configuration: " + e.getMessage());
        }
    }

    public void loadToken(){
        try(FileInputStream is = new FileInputStream(userConfigFile)) {
            Trace.out(Trace.Level.DEV, "Loading from file: " + userConfigFile);
            Properties prop = new Properties();
            prop.load(is);
            token = prop.getProperty("token");
            Trace.out(Trace.Level.DEV, "Loaded token: " + token);
        }catch (Exception e){
            Trace.out(Trace.Level.ERR, "Error in loading user configuration: " + e.getMessage());
        }
    }

    private void saveProperties(){

    }
}
