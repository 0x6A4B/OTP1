import util.ConfigSingleton;
import view.GUI;

public class Main {
    public static void main(String[] args) {
        //Client client = new Client()

        //GUI.setClient(client)
        ConfigSingleton.getInstance();
        GUI.launch(GUI.class);
        
        //LocaleSingleton.getInstance().setLocale(new Locale("fi", "FI"))
        //LocaleSingleton.getInstance().setLocale(new Locale("ar", "AE"))

        //AltView.launch(AltView.class)

        //Trace.out(Trace.Level.DEV, "Starting application")
    }
}