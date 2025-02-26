package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import model.Device;
import model.User;

public class DeviceController extends IController {

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML RadioButton radioDaily;
    @FXML RadioButton radioWeekly;
    @FXML RadioButton radioMontly;

    @FXML ChoiceBox<String> actionChoice;
    @FXML ChoiceBox<String> shareChoice;

    private User user = gui.getUser();
    private Device device = gui.getCurrentDevice();

    @Override
    public void start(){
        radioDaily.setToggleGroup(toggleGroup);
        radioWeekly.setToggleGroup(toggleGroup);
        radioMontly.setToggleGroup(toggleGroup);

        actionChoice.setValue("Select an action");
        shareChoice.setValue("Select a role");
    }
}