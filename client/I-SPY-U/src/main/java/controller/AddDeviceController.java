package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import view.GUI;

public class AddDeviceController extends IController {

    @FXML TextField uuid;
    @FXML TextField name;
    @FXML TextField desc;

    @FXML Button AddDeviceButton;

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

    @Override
    public void start(){
        AddDeviceButton.disableProperty().bind(uuid.textProperty().isEmpty().or(name.textProperty().isEmpty().or(desc.textProperty().isEmpty())));
    }
}
