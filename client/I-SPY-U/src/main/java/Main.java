import util.ConfigSingleton;
import util.LocaleSingleton;
import util.Trace;
import view.AltView;
import view.GUI;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        //Client client = new Client();

        //GUI.launch(GUI.class);
        //GUI.setClient(client);
        ConfigSingleton.getInstance();
        //LocaleSingleton.getInstance().setLocale(new Locale("fi", "FI"));
        LocaleSingleton.getInstance().setLocale(new Locale("ar", "AE"));

        AltView.launch(AltView.class);

        //Trace.out(Trace.Level.DEV, "Starting application");
    }
}