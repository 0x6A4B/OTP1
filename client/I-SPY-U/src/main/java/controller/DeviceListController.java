package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import view.GUI;

public class DeviceListController {

    private String currentDevice;

    @FXML private VBox myDevicesList;
    @FXML private VBox sharedDevicesList;

    @FXML private VBox sharedDeviceDetails;
    @FXML private VBox ownDeviceDetails;

    @FXML private Button openDeviceButton;

    @FXML private Label ownDeviceDetalsLabel;
    @FXML private ListView ownDeviceDetalsListview;

    @FXML private Label sharedDeviceDetalsLabel;
    @FXML private ListView sharedDeviceDetalsListview;
    
    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Label createNewDeviceLabel(String name, Boolean own){
        Label deviceLabel = new Label(name);
        deviceLabel.setAlignment(Pos.CENTER);
        deviceLabel.setContentDisplay(ContentDisplay.CENTER);
        deviceLabel.setPrefHeight(30.0);
        deviceLabel.setPrefWidth(185.0);
        deviceLabel.setStyle("-fx-border-color: Black;");
        if (own){
            deviceLabel.setOnMouseClicked(this::ownShowOwnDeviceDetails);
        } else {
            deviceLabel.setOnMouseClicked(this::sharedShowOwnDeviceDetails);
        }
        return deviceLabel;
    }

    @FXML
    private void openDevice(ActionEvent event){
        /* TODO: miten täs sais datan jos vaikka on vain devcies id?? ja sit jatkaa eteenpäin */
        System.out.println("Open own device"+currentDevice);
        try {
                GUI.setScene("Device", 500, 500);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    @FXML
    private void ownShowOwnDeviceDetails(MouseEvent event){
        for (Node node : myDevicesList.getChildren()) {
            if (node instanceof Label) {
            node.setStyle("-fx-background-color: white; -fx-border-color: Black;");
            }
        }
        ((Label) event.getSource()).setStyle("-fx-background-color: grey; -fx-border-color: Black;");
        ownDeviceDetails.setVisible(true);
        openDeviceButton.setVisible(true);
        ownDeviceDetalsLabel.setText(((Label) event.getSource()).getText());
        currentDevice = ((Label) event.getSource()).getText();
        ownDeviceDetalsListview.getItems().clear();
        /* TODO: miten sais ne recent readings (vaikka vikat 10) devicelle joka on valittu */
        for (int i = 0; i < 10; i++) {
            String date = "6.2.2025";
            String time = "14:00";
            char[] timeArray = time.toCharArray();
            timeArray[4] = (char) (timeArray[4] + i);
            time = new String(timeArray);
            ownDeviceDetalsListview.getItems().add(date+"  "+time+"  Detail " + i);
        }
    }

    @FXML
    private void sharedShowOwnDeviceDetails(MouseEvent event){
        for (Node node : sharedDevicesList.getChildren()) {
            if (node instanceof Label) {
            node.setStyle("-fx-background-color: white; -fx-border-color: Black;");
            }
        }
        ((Label) event.getSource()).setStyle("-fx-background-color: grey; -fx-border-color: Black;");
        sharedDeviceDetails.setVisible(true);
        openDeviceButton.setVisible(true);
        sharedDeviceDetalsLabel.setText(((Label) event.getSource()).getText());
        currentDevice = ((Label) event.getSource()).getText();
        sharedDeviceDetalsListview.getItems().clear();
        /* TODO: miten sais ne recent readings (vaikka vikat 10) devicelle joka on valittu */
        for (int i = 0; i < 10; i++) {
            String date = "5.2.2025";
            String time = "20:00";
            char[] timeArray = time.toCharArray();
            timeArray[4] = (char) (timeArray[4] + i);
            time = new String(timeArray);
            sharedDeviceDetalsListview.getItems().add(date+"  "+time+"  Detail " + i);
        }
    }

    @FXML
    private void initialize(){
        /* TODO: kato miten data tulee ja sen sais noihin labels sisään/kiinni niihin. vai laitetaanko vaa id siihe ja sit hakee aina erikseen */
        String response = "device1, device2, device3";
        String[] devices = response.split(",");
        for (String device : devices) {
            myDevicesList.getChildren().add(createNewDeviceLabel(device, true));
            sharedDevicesList.getChildren().add(createNewDeviceLabel(device, false));
        }
        ownDeviceDetails.setVisible(false);
        sharedDeviceDetails.setVisible(false);
        openDeviceButton.setVisible(false);
    }
}
