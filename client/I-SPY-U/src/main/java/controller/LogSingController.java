package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Person;
import model.User;

public class LogSingController extends IController {
    @FXML private TextField logInUsername;
    @FXML private TextField logInPassword;
    @FXML private Button logInButton;
    @FXML private Label logInErrorMsg;

    @FXML private TextField singUpEmail;
    @FXML private TextField singUpPassword;
    @FXML private Button singUpButton;
    @FXML private Label singUpErrorMsg;

    private User awnser;

    @FXML
    private void handleLogInButtonAction(ActionEvent event) {
        String username = logInUsername.getText();
        String password = logInPassword.getText();
        awnser = client.login(new User(username, password, new Person()));
        String error = "error happened";
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
        /* TODO add username, name(firstname lastname) street, city, postalcode to singup*/
        User user = new User("kekkonen", password, "active",
                new Person("Urho Kaleva", "Kekkonen", email,
                        "Kekkosenkatu 12", "Kekkoslovakia", "2222"));
        awnser = client.register(user);
        String error = "error happened";
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
        singUpButton.disableProperty().bind(singUpEmail.textProperty().isEmpty().or(singUpPassword.textProperty().isEmpty()));
        logInErrorMsg.setVisible(false);
        singUpErrorMsg.setVisible(false);
    }
}
