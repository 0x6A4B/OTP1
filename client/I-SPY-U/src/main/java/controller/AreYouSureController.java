package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class AreYouSureController extends IController {
    
    @FXML private Label popUpTitle;
    @FXML private Label warningText1;
    @FXML private Label warningText2;
    @FXML private Button continueButton;
    @FXML private Button cancelButton;
    
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

    @Override
    public void initialize(){
        //do nothingh
    }
}
