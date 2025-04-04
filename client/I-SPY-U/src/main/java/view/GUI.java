package view;

import java.io.IOException;

import controller.IController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Client;
import model.Device;
import model.DeviceShare;
import model.User;
import util.ConfigSingleton;

public class GUI extends Application {
    private Scene scene;
    private Stage popupStage;
    private Client service;
    private User user;
    private Device currentDevice;
    private DeviceShare currentShare;
    private IController kontrolleri;

    @Override
    public void start(Stage stage) throws Exception {
        // Checking if user token exists
        // Checks that config singleton has loaded the properties and there is
        // a token saved, otherwise need to login
        boolean tokenExists = ConfigSingleton.getInstance().configLoaded()
                && ConfigSingleton.getInstance().getToken() != null
                && !ConfigSingleton.getInstance().getToken().isEmpty();
        // End of check

        System.out.println("Loading FXML file...");

        if (!tokenExists)
            scene = new Scene(getLoader("LogSingUp"), 300, 400);
        else
            scene = new Scene(getLoader("DevicesList"), 500, 500);

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
        //IController
        kontrolleri = loader.getController();
        System.out.println("kontrolleri: "+loader.getController());
        kontrolleri.setGUI(this);
        kontrolleri.start();
        return loaded;
    }

    public void setScene(String fxml, int width, int height) throws IOException {
        scene.setRoot(getLoader(fxml));
        Stage stage = (Stage) scene.getWindow();
        stage.setWidth(width);
        stage.setHeight(height);
        stage.centerOnScreen();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setCurrentDevice(Device currentDevice) {
        this.currentDevice = currentDevice;
    }

    public Device getCurrentDevice() {
        return currentDevice;
    }


    // TODO: FIX THIS HACK
    private IController popupCtrl;

    public void openPopup(String fxml, int width, int height, IController popupCtrl) throws IOException {
        System.out.println("Opening popup "+fxml);
        popupStage = new Stage();   // we need to create new if popup is called again
        popupStage.initStyle(StageStyle.UNDECORATED);

        Parent parent = getLoader(fxml);

        // TODO: FIX UGLY HACK
        this.popupCtrl = popupCtrl;


        popupStage.setScene(new Scene(parent, width, height));
        popupStage.show();
    }

    public void closePopup() throws IOException {
        popupStage.close();
        popupCtrl.hook();
        popupCtrl = null;
    }
}