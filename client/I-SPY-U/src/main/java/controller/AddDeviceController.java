package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.GUI;

public class AddDeviceController {

    @FXML TextField uuid;
    @FXML TextField name;
    @FXML TextField desc;

    @FXML Button AddDeviceButton;

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML 
    private void handleAddDeviceButtonAction(){
        System.out.println("Adding device...");
        System.out.println(uuid.getText());
        System.out.println(name.getText());
        System.out.println(desc.getText());

        /* TODO tässä lisätään sitten uusi device userille */

        try {
                GUI.closePopup();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    private void initialize(){
        AddDeviceButton.disableProperty().bind(uuid.textProperty().isEmpty().or(name.textProperty().isEmpty().or(desc.textProperty().isEmpty())));
    }
}
