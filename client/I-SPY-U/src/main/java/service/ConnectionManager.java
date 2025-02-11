package service;

import http.HttpQuery;
import model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionManager {
    private String apiUrl;
    private String token;

    public ConnectionManager(){
        loadProperties();
    }

    private void loadProperties(){
        try(InputStream is = HttpQuery.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties prop = new Properties();
            prop.load(is);
            apiUrl = prop.getProperty("apiUrl");
            token = prop.getProperty("token");
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

        if (apiUrl == "")
            System.err.println("ERROR, no apiUrl property not found!");
        System.out.printf("prop: %s\ntoken: %s\n", apiUrl, token);
    }

    public User login(){
        return null;
    }

}
