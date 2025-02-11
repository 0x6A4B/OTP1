package http;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class HttpQuery {
    private String apiUrl;

    public HttpQuery(String apiUrl) { this.apiUrl = apiUrl; }

    //public void setApiUrl(String apiUrl){ this.apiUrl = apiUrl; }


}
