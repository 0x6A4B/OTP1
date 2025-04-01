package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AreYouSureController extends IController {
    
    @FXML private Label popUpTitle;
    @FXML private Label warningText1;
    @FXML private Label warningText2;
    @FXML private Button continueButton;
    @FXML private Button cancelButton;
    @FXML private AnchorPane mainBoio;
    
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
    public void translate(){
        System.out.println("Translating");
        popUpTitle.setText(localeSingleton.getTranslation("are_you_sure"));
        warningText1.setText(localeSingleton.getTranslation("you_are_removing_device"));
        warningText2.setText(localeSingleton.getTranslation("destructive_action"));
        continueButton.setText(localeSingleton.getTranslation("delete_device"));
        cancelButton.setText(localeSingleton.getTranslation("cancel"));
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
