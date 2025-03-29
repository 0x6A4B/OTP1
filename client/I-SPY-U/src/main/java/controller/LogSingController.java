package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import model.Person;
import model.User;
import util.Trace;

public class LogSingController extends IController {
    @FXML private TextField logInUsername;
    @FXML private PasswordField logInPassword;
    @FXML private Button logInButton;
    @FXML private Label logInErrorMsg;
    @FXML private CheckBox logInRememberMe;

    @FXML private TextField singUpEmail;
    @FXML private PasswordField singUpPassword;
    @FXML private TextField singUpUSername;
    @FXML private TextField singUpCity;
    @FXML private TextField singUpPostalCode;
    @FXML private Button singUpButton;
    @FXML private Label singUpErrorMsg;
    @FXML private CheckBox singUpRememberMe;

    //these are for chanhing text for localization
    @FXML private Tab logInTab;
    @FXML private Label logInLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;

    @FXML private Tab signUpTab;
    @FXML private Label signUpLabel;
    @FXML private Label signUpPasswordLabel;
    @FXML private Label signUpUsernameLabel;
    @FXML private Label signUpEmailLabel;
    @FXML private Label signUpCityLabel;
    @FXML private Label signUpPostalcodeLabel;

    private User awnser;

    @FXML
    private void handleLogInButtonAction(ActionEvent event) {
        String username = logInUsername.getText();
        String password = logInPassword.getText();
        String error = "error happened";
        if (logInRememberMe.isSelected()){
            Trace.out(Trace.Level.DEV, "Remember user");
            client.setRememberUser(true);
        }
        awnser = client.login(new User(username, password, new Person()));
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
        // TODO: get user's name and address => faking it for now
        User user = new User(username, password, "active",
                new Person("Urho Kaleva", "Kekkonen", email,
                        "Suomen maa kunta", city, postalCode));
        awnser = client.register(user);
        String error = "error happened";
        if (singUpRememberMe.isSelected()){
            client.setRememberUser(true);
        }
        if (awnser != null) {
            try {
                gui.setUser(awnser);
                client.login(user); // logging in with new user
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
    public void translate(){
        System.out.println("Translating");
        
        logInTab.setText(localeSingleton.getTranslation("login"));
        logInLabel.setText(localeSingleton.getTranslation("login"));
        usernameLabel.setText(localeSingleton.getTranslation("username"));
        passwordLabel.setText(localeSingleton.getTranslation("password"));
        logInButton.setText(localeSingleton.getTranslation("login"));
        logInRememberMe.setText(localeSingleton.getTranslation("remember_me"));
        logInErrorMsg.setText(localeSingleton.getTranslation("error happened"));

        signUpTab.setText(localeSingleton.getTranslation("signup"));
        signUpLabel.setText(localeSingleton.getTranslation("signup"));
        signUpUsernameLabel.setText(localeSingleton.getTranslation("username"));
        signUpPasswordLabel.setText(localeSingleton.getTranslation("password"));
        signUpEmailLabel.setText(localeSingleton.getTranslation("email"));
        signUpCityLabel.setText(localeSingleton.getTranslation("city"));
        signUpPostalcodeLabel.setText(localeSingleton.getTranslation("postalcode"));
        singUpButton.setText(localeSingleton.getTranslation("signup"));
        singUpRememberMe.setText(localeSingleton.getTranslation("remember_me"));
        singUpErrorMsg.setText(localeSingleton.getTranslation("error happened"));
    }

    @Override
    public void start() {
        logInButton.disableProperty().bind(logInUsername.textProperty().isEmpty().or(logInPassword.textProperty().isEmpty()));
        singUpButton.disableProperty().bind(singUpEmail.textProperty().isEmpty().or(singUpPassword.textProperty().isEmpty().or(singUpUSername.textProperty().isEmpty().or(singUpCity.textProperty().isEmpty().or(singUpPostalCode.textProperty().isEmpty())))));
        logInErrorMsg.setVisible(false);
        singUpErrorMsg.setVisible(false);

        /* fill fields for easier testing so no need to fill them out */
        //logInUsername.setText("wasdi");
        //logInPassword.setText("wasdi");

        /* singUpEmail.setText("emaili");
        singUpPassword.setText("passwordi");
        singUpUSername.setText("usernami");
        singUpCity.setText("cityni");
        singUpPostalCode.setText("postalcodeni"); */

        //mirrorUI();
    }
}
