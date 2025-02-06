package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.GUI;

public class LogSingController {
    @FXML private TextField logInEmail;
    @FXML private TextField logInPassword;
    @FXML private Button logInButton;
    @FXML private Label logInErrorMsg;

    @FXML private TextField singUpEmail;
    @FXML private TextField singUpUsername;
    @FXML private TextField singUpPassword;
    @FXML private Button singUpButton;
    @FXML private Label singUpErrorMsg;
    
    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void handleLogInButtonAction(ActionEvent event) {
        String email = logInEmail.getText();
        String password = logInPassword.getText();
        //here we send the logIn to motor and then api
        Boolean awnser = true;
        String error = "error happened";
        if (awnser) {
            //awnser is the response if log in is fine
            try {
                GUI.setScene("DevicesList", 500, 500);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            setErrorMsg(error);
        }
    }
    private void handleSingUpButtonAction(ActionEvent event) {
        String email = singUpEmail.getText();
        String username = singUpUsername.getText();
        String password = singUpPassword.getText();
        //here we send the SingUp to motor and then api
        Boolean awnser = true;
        String error = "error happened";
        if (awnser) {
            //awnser is the response if log in is fine
            try {
                GUI.setScene("DevicesList", 500, 500);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            setErrorMsg(error);
        }
    }

    public void setErrorMsg(String msg) {
        logInErrorMsg.setVisible(true);
        singUpErrorMsg.setVisible(true);
        logInErrorMsg.setText(msg);
        singUpErrorMsg.setText(msg);
    }

    @FXML
    private void initialize() {
        logInButton.disableProperty().bind(logInEmail.textProperty().isEmpty().or(logInPassword.textProperty().isEmpty()));
        singUpButton.disableProperty().bind(singUpEmail.textProperty().isEmpty().or(singUpUsername.textProperty().isEmpty()).or(singUpPassword.textProperty().isEmpty()));
        logInErrorMsg.setVisible(false);
        singUpErrorMsg.setVisible(false);
    }
}
