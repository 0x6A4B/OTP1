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
import model.User;
import util.ConfigSingleton;

public class GUI extends Application {
    private /*static*/ Scene scene;
    private /*static*/ Stage popupStage;
    private /*static*/ Client service;
    private /*static*/ User user;
    private /*static*/ Device currentDevice;
    private IController kontrolleri;

    @Override
    public void start(Stage stage) throws Exception {
        /* TODO: check here if user is already set and depending on it then launch login or devicelist */
        // Checking if user token exists
        // Checks that config singleton has loaded the properties and there is
        // a token saved, otherwise need to login
        boolean tokenExists = ConfigSingleton.getInstance().configLoaded()
                && !ConfigSingleton.getInstance().getToken().isEmpty();
        // End of check

        System.out.println("Loading FXML file...");
        //scene = new Scene(getLoader("MainView"), 300, 300);

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
//        kontrolleri.setClient(GUI.service);
        kontrolleri.setGUI(this);
        kontrolleri.start();
        return loaded;
    }

    public /*static*/ void setScene(String fxml, int width, int height) throws IOException {
        scene.setRoot(getLoader(fxml));
        Stage stage = (Stage) scene.getWindow();
        stage.setWidth(width);
        stage.setHeight(height);
        stage.centerOnScreen();
    }

    public /*static*/ void setUser(User user) {
        //GUI.
        this.user = user;
    }

    public /*static*/ User getUser() {
        return this.user;
    }

    public /*static*/ void setCurrentDevice(Device currentDevice) {
        this.currentDevice = currentDevice;
    }

    public /*static*/ Device getCurrentDevice() {
        return currentDevice;
    }

    // TODO: FIX THIS HACK
    private IController popupCtrl;

    public /*static*/ void openPopup(String fxml, int width, int height, IController popupCtrl) throws IOException {
        popupStage = new Stage();   // we need to create new if popup is called again
        popupStage.initStyle(StageStyle.UNDECORATED);

        Parent parent = getLoader(fxml);

        // TODO: FIX UGLY HACK
        this.popupCtrl = popupCtrl;


        popupStage.setScene(new Scene(parent, width, height));
        popupStage.show();
    }

    public /*static*/ void closePopup() throws IOException {
        popupStage.close();
        popupCtrl.hook();
        popupCtrl = null;
    }

    public void popupHook(){

    }


}