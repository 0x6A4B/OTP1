package service;

import model.Device;
import model.User;

import java.util.List;

public class ConnectionManager {
    private String apiUrl;
    private String token;

    //private UserQuery userQuery;
    private UserManager userManager;
    private DeviceManager deviceManager = new DeviceManager();

    public ConnectionManager(){
        /*try {
            loadProperties();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }

    /*
    private void loadProperties() throws Exception{
        try(InputStream is = ConnectionManager.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties prop = new Properties();
            prop.load(is);
            apiUrl = prop.getProperty("apiUrl").replaceAll("\"", "");
            if (apiUrl.isEmpty()) {
                // eerror in reading apirul property
                throw new Exception("Error in reading property: apiurl");
            }
            System.out.println("Loaded config apiurl: " + apiUrl);
            ConfigSingleton.getInstance().setApiUrl(apiUrl);

            token = prop.getProperty("token");
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

        if (apiUrl == "")
            System.err.println("ERROR, no apiUrl property not found!");
        System.out.printf("prop: %s\ntoken: %s\n", apiUrl, token);
    }

     */

    public User login(User user){
        System.out.println("conmgr.login");
        System.out.println("apiurl: " + apiUrl);
        if (userManager == null)
            userManager = new UserManager(apiUrl);
        userManager.login(user);
        return null;
    }

    public User register(User user){
        if (userManager == null){
            userManager = new UserManager(apiUrl);
        }
        userManager.register(user);
        return null;
    }

    public List<Device> getDevices(){
        System.out.println("conmgr.getdevices");
        return deviceManager.readAll(new Object());
        //List<Device> = dev.stream().map(o -> (Device) o).toList();
    }

    
    /*
    public List<Object> getAll(String s){
        List<Object> lsit = switch(s){
            case "device" -> yield deviceMgr.get();
            case "logentry" -> yield logmgr.get();
        }
        return new ArrayList<>();
    }
    */

}
