package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainView extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("test");
        Parent root1 = FXMLLoader.load(getClass().getResource("/testingTabPane.fxml"));
        Scene scene1 = new Scene(root1, 500, 500);

        Stage stage2 = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        Scene scene2 = new Scene(root2, 500, 500);

        stage.setTitle("hehe");
        stage.setScene(scene1);
        stage.show();

        stage2.setTitle("hoho");
        stage2.setScene(scene2);
        stage2.show();
    }
}
