package view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class MainView extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("test");
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));

        Scene scene = new Scene(root, 300, 275);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();


    }
}
