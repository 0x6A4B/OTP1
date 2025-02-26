package view;

import java.io.IOException;

import controller.IController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Device;
import model.TrustMeBraWhyWouldILie;
import model.User;

public class GUI extends Application {
    private static Scene scene;
    private static Stage popupStage = new Stage();
    private static TrustMeBraWhyWouldILie service = new TrustMeBraWhyWouldILie();
    private static User user;
    private static Device currentDevice;

    @Override
    public void start(Stage stage) throws Exception {
        /* TODO: check here if user is already set and depending on it then launch login or devicelist */
        System.out.println("Loading FXML file...");
        scene = new Scene(getLoader("MainView"), 300, 300);
        System.out.println("FXML file loaded successfully.");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("I-SPY-U");
        stage.setScene(scene);
        stage.show();
    }

    public Parent getLoader(String fxml) {
        Parent loaded = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxml + ".fxml"));
        try {
            loaded = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        IController kontrolleri = loader.getController();
        kontrolleri.testing();
        System.out.println("kontrolleri: "+loader.getController());
        return loaded;
    }

    public static void setScene(String fxml, int width, int height) throws IOException {
        scene.setRoot(new GUI().getLoader(fxml));
        Stage stage = (Stage) scene.getWindow();
        stage.setWidth(width);
        stage.setHeight(height);
        stage.centerOnScreen();
    }

    public static void setUser(User user) {
        GUI.user = user;
    }

    public static User getUser() {
        return GUI.user;
    }

    public static void setCurrentDevice(Device currentDevice) {
        GUI.currentDevice = currentDevice;
    }

    public static Device getCurrentDevice() {
        return GUI.currentDevice;
    }

    public static TrustMeBraWhyWouldILie getService() {
        return GUI.service;
    }

    public static void openPopup(String fxml, int width, int height) throws IOException {
        popupStage.initStyle(StageStyle.UNDECORATED);
        popupStage.setScene(new Scene(FXMLLoader.load(GUI.class.getResource("/" + fxml + ".fxml")), width, height));
        popupStage.show();
    }

    public static void closePopup() throws IOException {
        popupStage.close();
    }
}