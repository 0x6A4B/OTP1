package controller;

import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Device;
import util.ConfigSingleton;
import util.Trace;

public class AddDeviceController extends AbstractController {

    @FXML private TextField uuid;
    @FXML private TextField name;
    @FXML private TextField desc;
    @FXML private Label errorMsg;
    @FXML private Button addDeviceButton;

    //these are for chanhing text for localization
    @FXML private Label addDeviceTitle;
    @FXML private Label addDeviceUUID;
    @FXML private Label addDeviceName;
    @FXML private Label addDeviceDescription;

    @FXML
    private void handleAddDeviceButtonAction() {
        Trace.out(Trace.Level.DEV, "Adding device...");
        Trace.out(Trace.Level.DEV, String.valueOf(uuid.getText()));
        Trace.out(Trace.Level.DEV, String.valueOf(name.getText()));
        Trace.out(Trace.Level.DEV, String.valueOf(desc.getText()));

        try {
            UUID realUuid = UUID.fromString(uuid.getText());
            client.addDevice(new Device(ConfigSingleton.getInstance().getUser(),
                                        realUuid,
                                        name.getText(),
                                        true,
                                        desc.getText(),
                                        "model"));
            gui.closePopup();
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
    public void start() {
        errorMsg.setVisible(false);
        addDeviceButton.disableProperty().bind(uuid.textProperty().isEmpty().or(name.textProperty().isEmpty().or(desc.textProperty().isEmpty())));
    }

    @Override
    public void translate() {
        Trace.out(Trace.Level.DEV, "Translating");
        addDeviceTitle.setText(localeSingleton.getTranslation("add_device"));
        addDeviceUUID.setText(localeSingleton.getTranslation("uuid"));
        addDeviceName.setText(localeSingleton.getTranslation("name"));
        addDeviceDescription.setText(localeSingleton.getTranslation("description"));
        addDeviceButton.setText(localeSingleton.getTranslation("add_device"));
    }

    @Override
    public void initialize() {
        if (localeSingleton.isRightToLeft()) {
            mainBoio.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            mainBoio.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
        translate();
    }
}
