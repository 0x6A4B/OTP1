package controller;

import java.io.IOException;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Device;
import util.ConfigSingleton;
import util.Trace;

public class AddDeviceController extends IController {

    @FXML TextField uuid;
    @FXML TextField name;
    @FXML TextField desc;

    @FXML Label errorMsg;

    @FXML Button AddDeviceButton;

    @FXML 
    private void handleAddDeviceButtonAction(){
        System.out.println("Adding device...");
        System.out.println(uuid.getText());
        System.out.println(name.getText());
        System.out.println(desc.getText());


        try{
            UUID realUuid = UUID.fromString(uuid.getText());
            /* TODO tässä lisätään sitten uusi device userille */
            client.addDevice(new Device(ConfigSingleton.getInstance().getUser(), realUuid, name.getText(), true, desc.getText(), "model"));
        } catch (Exception e) {
            errorMsg.setText("Failed to create UUID, was it valid?!");
            Trace.out(Trace.Level.ERR, "Failed to create UUID, was it valid?!");
        }

        try {
                // TODO: Must refresh devicelist and window
                gui.closePopup();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void start(){
        errorMsg.setText("");
        AddDeviceButton.disableProperty().bind(uuid.textProperty().isEmpty().or(name.textProperty().isEmpty().or(desc.textProperty().isEmpty())));
    }
}
