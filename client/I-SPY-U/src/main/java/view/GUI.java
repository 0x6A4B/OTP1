package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUI extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Loading FXML file...");
        scene = new Scene(FXMLLoader.load(getClass().getResource("/MainView.fxml")), 300, 300);
        System.out.println("FXML file loaded successfully.");

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("I-SPY-U");
        stage.setScene(scene);
        stage.show();
    }

    public static void setScene(String fxml, int width, int height) throws IOException {
        scene.setRoot(FXMLLoader.load(GUI.class.getResource("/" + fxml + ".fxml")));
        Stage stage = (Stage) scene.getWindow();
        stage.setWidth(width);
        stage.setHeight(height);
    }
}