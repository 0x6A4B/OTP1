package controller;

import java.io.IOException;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Device;
import util.ConfigSingleton;
import util.Trace;

public class AddDeviceController extends IController {

    @FXML TextField uuid;
    @FXML TextField name;
    @FXML TextField desc;

    @FXML private AnchorPane mainBoio;

    @FXML Label errorMsg;

    @FXML Button AddDeviceButton;

    //these are for chanhing text for localization
    @FXML private Label addDeviceTitle;
    @FXML private Label addDeviceUUID;
    @FXML private Label addDeviceName;
    @FXML private Label addDeviceDescription;

    @FXML 
    private void handleAddDeviceButtonAction(){
        System.out.println("Adding device...");
        System.out.println(uuid.getText());
        System.out.println(name.getText());
        System.out.println(desc.getText());


        try{
            UUID realUuid = UUID.fromString(uuid.getText());
            client.addDevice(new Device(ConfigSingleton.getInstance().getUser(), realUuid, name.getText(), true, desc.getText(), "model"));
            try {
                gui.closePopup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            errorMsg.setVisible(true);
            errorMsg.setText("Failed to create UUID, was it valid?");
            Trace.out(Trace.Level.ERR, "Failed to create UUID, was it valid?!");
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void start(){
        errorMsg.setVisible(false);
        AddDeviceButton.disableProperty().bind(uuid.textProperty().isEmpty().or(name.textProperty().isEmpty().or(desc.textProperty().isEmpty())));
        //mirrorUI();
    }

    @Override
    public void translate(){
        System.out.println("Translating");
        addDeviceTitle.setText(localeSingleton.getTranslation("add_device"));
        addDeviceUUID.setText(localeSingleton.getTranslation("uuid"));
        addDeviceName.setText(localeSingleton.getTranslation("name"));
        addDeviceDescription.setText(localeSingleton.getTranslation("description"));
        AddDeviceButton.setText(localeSingleton.getTranslation("add_device"));
    }

    @Override
    public void initialize(){
        if (localeSingleton.isRightToLeft()) {
            mainBoio.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            mainBoio.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
        translate();
    }
}
