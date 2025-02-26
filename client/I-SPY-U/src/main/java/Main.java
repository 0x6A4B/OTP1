import http.HttpQuery;
import http.UserQuery;
import model.Client;
import model.Person;
import model.User;
import service.ConnectionManager;
import view.GUI;

public class Main {
    public static void main(String[] args) {
        //GUI.launch(GUI.class);

        //httpquery exmaple
        //HttpQuery q = new UserQuery("https://otp1.0x6a4b.dev/api");
        //if (q.post())
        //    q.getDev();

        //ConnectionManager cm = new ConnectionManager();
        //cm.login();

        User newUser = new User("jope2", "jope2", "",
                new Person("jope2", "ruonansuu2", "jope@mail.no"));

        User wasdi = new User("wasdi", "wasdi", "", new Person());

        Client client = new Client();
        //client.register(newUser);
        client.login(wasdi);
        client.getDevices(wasdi);
        //client.login(newUser);
    }
}