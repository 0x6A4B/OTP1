import model.Client;
import util.Trace;
import view.GUI;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        Trace.out(Trace.Level.DEV, "Starting application");

        /* TODO: with remember me first setUser if user is found from remmeber me thingy */
        GUI.launch(GUI.class);
    }
}