import javafx.application.Application;
import util.ConfigSingleton;
import view.GUI;

public class Main {
    public static void main(String[] args) {
        ConfigSingleton.getInstance();
        Application.launch(GUI.class);
    }
}