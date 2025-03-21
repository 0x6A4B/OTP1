package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AreYouSureController extends IController {
    
    @FXML
    private void handleRemoveDevice(){
        System.out.println("Removing device "+gui.getCurrentDevice().getName());
        client.removeDevice(gui.getCurrentDevice());
        try {
            gui.closePopup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
