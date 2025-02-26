package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Device;
import model.TrustMeBraWhyWouldILie;
import model.User;
import view.GUI;

public class DeviceController {

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML RadioButton radioDaily;
    @FXML RadioButton radioWeekly;
    @FXML RadioButton radioMontly;

    @FXML ChoiceBox<String> actionChoice;
    @FXML ChoiceBox<String> shareChoice;

    private static TrustMeBraWhyWouldILie service = GUI.getService();
    private static User user = GUI.getUser();
    private static Device device = GUI.getCurrentDevice();

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize(){
        radioDaily.setToggleGroup(toggleGroup);
        radioWeekly.setToggleGroup(toggleGroup);
        radioMontly.setToggleGroup(toggleGroup);

        actionChoice.setValue("Select an action");
        shareChoice.setValue("Select a role");
    }
}