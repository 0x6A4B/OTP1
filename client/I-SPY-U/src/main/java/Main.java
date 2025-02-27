import http.HttpQuery;
import http.UserQuery;
import model.Client;
import model.Device;
import model.Person;
import model.User;
import service.ConnectionManager;
import util.ConfigSingleton;
import util.Trace;
import view.GUI;

public class Main {
    public static void main(String[] args) {


        //GUI.launch(GUI.class);


        User newUser = new User("jope2", "jope2", "",
                new Person("jope2", "ruonansuu2", "jope@mail.no"));

        User wasdi = new User("wasdi", "wasdi", "", new Person());




        Client client = new Client();

        while(!ConfigSingleton.getInstance().configLoaded())
            System.out.println("waiting...");

        Trace.out(Trace.Level.DEV, "Starting application");

        //client.register(newUser);
        client.login(wasdi);
        Device device = client.getDevices(wasdi).getFirst();
        client.getLogEntries(device).forEach(System.out::println);
        //client.login(newUser);

    }
}