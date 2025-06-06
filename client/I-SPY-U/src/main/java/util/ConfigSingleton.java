package util;

import model.User;
import java.io.*;
import java.util.Properties;

public class ConfigSingleton {
    private static ConfigSingleton instance = new ConfigSingleton();
    private String token = "";
    private String apiUrl;
    private boolean configLoaded = false;
    private final String configFile = "app.properties";

    /*
        How am I supposed to work with config files when program is packaged as JAR?
        All solutions seem bad and hacky. Don't want to save to the directory where .JAR
        is called from. Or should I just expect to use the working directory?
        Maybe...
        The solutions that work perfectly with unpackaged files break with JAR
     */

    // Saving config file to directory .JAR is run from aka working directory
    // TODO: Should probably use this instead => private final String userConfigFile = "app.cfg";

    // Saving to the directory where .JAR file is located... Maybe bad idea? Would end up in /bin/ if .JAR is there
    private final String userConfigFileName = "app.cfg";
    private final String jarDirectory = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
            .substring(0, this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().lastIndexOf("/"));
    private final String userConfigFile = jarDirectory + "/" + userConfigFileName;

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
        this.token = token;
        Trace.out(Trace.Level.DEV,"TOKEN has been set: " + token);
    }
    public void setApiUrl(String apiUrl){
        this.apiUrl = apiUrl;
        Trace.out(Trace.Level.DEV,"APIRUL has been set: " + apiUrl);
    }

    public String getToken(){ return token; }
    public String getApiUrl(){
        Trace.out(Trace.Level.DEV,"API URL has been called: " + apiUrl);
        return apiUrl;
    }

    private void loadProperties(){
        try(InputStream is = ConfigSingleton.class.getClassLoader().getResourceAsStream(configFile)) {
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
                default -> Trace.setTraceLevel(Trace.Level.ERR);
            }

            if (apiUrl.isEmpty()) {
                // eerror in reading apirul property
                Trace.out(Trace.Level.ERR,"Error in reading prop: apiurl");
            }
            Trace.out(Trace.Level.DEV,"Loaded config apiurl: " + apiUrl);
            loadToken();
            configLoaded = true;
        }catch (IOException e){
            Trace.out(Trace.Level.ERR, "Error in loading configuration" + e.getMessage());
        }

        if (apiUrl.isEmpty())
            Trace.out(Trace.Level.ERR,"ERROR, no apiUrl property not found!");
        Trace.out(Trace.Level.DEV,"prop: " + apiUrl + "\ntoken: " + token);
    }

    // Remember logged in user
    public void saveToken(){
        try (OutputStream os = new FileOutputStream(userConfigFile)) {
            Properties prop = new Properties();
            prop.setProperty("token", token);
            Trace.out(Trace.Level.DEV, "Saving to file: " + userConfigFile);
            prop.store(os, null);
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
        // Not implemented yet, maybe not possible to save into the .properties -file?
        // TODO: Probably smarter to use java's preferences library!!
    }
}
