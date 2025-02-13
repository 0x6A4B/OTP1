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
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.GUI;

public class DeviceListController {

    private String currentDevice;

    private VBox DevicesList;
    private VBox DeviceDetails;
    private Label DeviceDetailsLabel;
    private ListView DeviceDetailsListview;

    @FXML private VBox myDevicesList;
    @FXML private VBox sharedDevicesList;

    @FXML private Tab ownDevicesTab;

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

    private Label createNewDeviceLabel(String name){
        Label deviceLabel = new Label(name);
        deviceLabel.setAlignment(Pos.CENTER);
        deviceLabel.setContentDisplay(ContentDisplay.CENTER);
        deviceLabel.setPrefHeight(30.0);
        deviceLabel.setPrefWidth(185.0);
        deviceLabel.setStyle("-fx-border-color: Black;");
        deviceLabel.setOnMouseClicked(this::ShowOwnDeviceDetails);
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
    private void switchToSharedDevices(){
        this.DevicesList = sharedDevicesList;
        this.DeviceDetails = sharedDeviceDetails;
        this.DeviceDetailsLabel = sharedDeviceDetalsLabel;
        this.DeviceDetailsListview = sharedDeviceDetalsListview;
    }

    @FXML
    private void switchToOwnDevices(){
        this.DevicesList = myDevicesList;
        this.DeviceDetails = ownDeviceDetails;
        this.DeviceDetailsLabel = ownDeviceDetalsLabel;
        this.DeviceDetailsListview = ownDeviceDetalsListview;
    }

    @FXML
    private void ShowOwnDeviceDetails(MouseEvent event){
        for (Node node : DevicesList.getChildren()) {
            if (node instanceof Label) {
            node.setStyle("-fx-background-color: white; -fx-border-color: Black;");
            }
        }
        ((Label) event.getSource()).setStyle("-fx-background-color: grey; -fx-border-color: Black;");
        DeviceDetails.setVisible(true);
        openDeviceButton.setVisible(true);
        DeviceDetailsLabel.setText(((Label) event.getSource()).getText());
        currentDevice = ((Label) event.getSource()).getText();
        DeviceDetailsListview.getItems().clear();
        /* TODO: miten sais ne recent readings (vaikka vikat 10) devicelle joka on valittu */
        for (int i = 0; i < 10; i++) {
            String date = "6.2.2025";
            String time = "14:00";
            char[] timeArray = time.toCharArray();
            timeArray[4] = (char) (timeArray[4] + i);
            time = new String(timeArray);
            DeviceDetailsListview.getItems().add(date+"  "+time+"  Detail " + i);
        }
    }

    private void addNewDevice(MouseEvent event){
        try {
            GUI.openPopup("AddDeviceWindow", 300, 350);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Label addNewDeviceButton(){
        Label newdeviceLabel = new Label("+ Add new device");
        newdeviceLabel.setAlignment(Pos.CENTER);
        newdeviceLabel.setContentDisplay(ContentDisplay.CENTER);
        newdeviceLabel.setPrefHeight(30.0);
        newdeviceLabel.setPrefWidth(185.0);
        newdeviceLabel.setStyle("-fx-border-color: grey;");
        newdeviceLabel.setOnMouseClicked(this::addNewDevice);
        return newdeviceLabel;
    }

    private void getDevices(VBox boksi, String response/* response vaa täyttää tässä */){
        /* TODO: tässä pitäis hakee ne devicet ja laittaa ne tohon boksiin
        pitää kattoo jos siihen saa jotenki dictionary tyylisesti
        jotta voidaan saada ehkä id siihen mukaan ja sit se device ikkunalle eteenpäin */
        String[] devices = response.split(",");
        for (String device : devices) {
            boksi.getChildren().add(createNewDeviceLabel(device));
        }
    }

    public void refreshDevices(){
        String ownresponse = "own device1, own device2, own device3";
        String sharedresponse = "shared device1, shared device2, shared device3";

        getDevices(myDevicesList, ownresponse);
        getDevices(sharedDevicesList, sharedresponse);
    }

    @FXML
    private void initialize(){
        refreshDevices();

        myDevicesList.getChildren().add(addNewDeviceButton());
        sharedDevicesList.getChildren().add(addNewDeviceButton());
        ownDeviceDetails.setVisible(false);
        sharedDeviceDetails.setVisible(false);
        openDeviceButton.setVisible(false);

        this.DevicesList = myDevicesList;
        this.DeviceDetails = ownDeviceDetails;
        this.DeviceDetailsLabel = ownDeviceDetalsLabel;
        this.DeviceDetailsListview = ownDeviceDetalsListview;

        ownDevicesTab.setOnSelectionChanged(e -> {
            System.err.println("Tab changed own devices tab is: "+ownDevicesTab.isSelected());
            if (ownDevicesTab.isSelected()) {
                switchToOwnDevices();
            } else {
                switchToSharedDevices();
            }
        });
    }
}
