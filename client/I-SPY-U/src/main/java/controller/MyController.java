package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import view.GUI;

public class MyController {

    public void showLogSingUP() {
        try {
            GUI.setScene("LogSingUp", 300, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDeviceList() {
        try {
            GUI.setScene("DevicesList", 500, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDevice() {
        try {
            GUI.setScene("Device", 500, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTest() {
        try {
            GUI.setScene("Test", 500, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCloseButtonAction(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}