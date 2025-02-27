package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Person;
import model.User;

public class LogSingController extends IController {
    @FXML private TextField logInUsername;
    @FXML private TextField logInPassword;
    @FXML private Button logInButton;
    @FXML private Label logInErrorMsg;
    @FXML private CheckBox logInRememberMe;

    @FXML private TextField singUpEmail;
    @FXML private TextField singUpPassword;
    @FXML private TextField singUpUSername;
    @FXML private TextField singUpCity;
    @FXML private TextField singUpPostalCode;
    @FXML private Button singUpButton;
    @FXML private Label singUpErrorMsg;
    @FXML private CheckBox singUpRememberMe;

    private User awnser;

    @FXML
    private void handleLogInButtonAction(ActionEvent event) {
        String username = logInUsername.getText();
        String password = logInPassword.getText();
        awnser = client.login(new User(username, password, new Person()));
        String error = "error happened";
        if (logInRememberMe.isSelected()){
            /* TODO tässä sit laitetaan jos haluu muistaa loginin */
        }
        if (awnser != null) {
            try {
                gui.setUser(awnser);
                gui.setScene("DevicesList", 500, 500);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            setErrorMsg(error);
        }
    }

    @FXML
    private void handleSingUpButtonAction(ActionEvent event) {
        String email = singUpEmail.getText();
        String password = singUpPassword.getText();
        String username = singUpUSername.getText();
        String city = singUpCity.getText();
        String postalCode = singUpPostalCode.getText();
        User user = new User(username, password, "active",
                new Person("Urho Kaleva", "Kekkonen", email,
                        "Suomen maa kunta", city, postalCode));
        awnser = client.register(user);
        String error = "error happened";
        if (singUpRememberMe.isSelected()){
            /* TODO tässä sit laitetaan jos haluu muistaa loginin */
        }
        if (awnser != null) {
            try {
                gui.setUser(awnser);
                gui.setScene("DevicesList", 500, 500);
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

    @Override
    public void start() {
        logInButton.disableProperty().bind(logInUsername.textProperty().isEmpty().or(logInPassword.textProperty().isEmpty()));
        singUpButton.disableProperty().bind(singUpEmail.textProperty().isEmpty().or(singUpPassword.textProperty().isEmpty().or(singUpUSername.textProperty().isEmpty().or(singUpCity.textProperty().isEmpty().or(singUpPostalCode.textProperty().isEmpty())))));
        logInErrorMsg.setVisible(false);
        singUpErrorMsg.setVisible(false);
    }
}
