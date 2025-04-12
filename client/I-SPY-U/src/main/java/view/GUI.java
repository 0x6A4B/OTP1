package view;

import java.io.IOException;

import controller.AbstractController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Device;
import model.User;
import util.ConfigSingleton;
import util.Trace;

public class GUI extends Application {
    private Scene scene;
    private Stage popupStage;
    private User user;
    private Device currentDevice;
    private AbstractController kontrolleri;

    @Override
    public void start(Stage stage) throws Exception {
        // Checking if user token exists
        // Checks that config singleton has loaded the properties and there is
        // a token saved, otherwise need to login
        boolean tokenExists = ConfigSingleton.getInstance().configLoaded()
                && ConfigSingleton.getInstance().getToken() != null
                && !ConfigSingleton.getInstance().getToken().isEmpty();
        // End of check

        Trace.out(Trace.Level.DEV, "Loading FXML file...");

        if (!tokenExists)
            scene = new Scene(getLoader("LogSingUp"), 300, 400);
        else
            scene = new Scene(getLoader("DevicesList"), 500, 500);

        Trace.out(Trace.Level.DEV, "FXML file loaded successfully.");
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
            Trace.out(Trace.Level.ERR, "Failed to get load: " + e.getMessage());
        }
        //IController
        kontrolleri = loader.getController();
        Trace.out(Trace.Level.DEV, "kontrolleri: "+loader.getController());
        kontrolleri.setGUI(this);
        kontrolleri.start();
        return loaded;
    }

    public void setScene(String fxml, int width, int height) {
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
    private AbstractController popupCtrl;

    public void openPopup(String fxml, int width, int height, AbstractController popupCtrl) {
        Trace.out(Trace.Level.DEV, "Opening popup "+fxml);
        popupStage = new Stage();   // we need to create new if popup is called again
        popupStage.initStyle(StageStyle.UNDECORATED);

        Parent parent = getLoader(fxml);

        // TODO: FIX UGLY HACK
        this.popupCtrl = popupCtrl;


        popupStage.setScene(new Scene(parent, width, height));
        popupStage.show();
    }

    public void closePopup() {
        popupStage.close();
        popupCtrl.hook();
        popupCtrl = null;
    }
}