package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

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
import model.Device;
import model.LogEntry;
import util.Trace;

public class DeviceListController extends IController {

    private VBox DevicesList;
    private VBox DeviceDetails;
    private Label DeviceDetailsLabel;
    private ListView DeviceDetailsListview;
    private Button openDeviceButton;

    @FXML private VBox myDevicesList;
    @FXML private VBox sharedDevicesList;

    @FXML private Tab ownDevicesTab;
    @FXML private Tab sharedDevicesTab;

    @FXML private VBox sharedDeviceDetails;
    @FXML private VBox ownDeviceDetails;

    @FXML private Button sharedOpenDeviceButton;
    @FXML private Button ownOpenDeviceButton;

    @FXML private Button removeDeviceButton;

    @FXML private Label ownDeviceDetalsLabel;
    @FXML private ListView ownDeviceDetalsListview;

    @FXML private Label sharedDeviceDetalsLabel;
    @FXML private ListView sharedDeviceDetalsListview;

    private List<Device> devices;
    private Device currentDevice;

    private int logEntriesCount = 0;
    @FXML private Label logEntriesCountLabel;

    private Label createNewDeviceLabel(Device dev){
        Label deviceLabel = new Label(dev.getName());
        deviceLabel.setAlignment(Pos.CENTER);
        deviceLabel.setContentDisplay(ContentDisplay.CENTER);
        deviceLabel.setPrefHeight(30.0);
        deviceLabel.setPrefWidth(185.0);
        deviceLabel.setStyle("-fx-border-color: Black;");
        deviceLabel.setOnMouseClicked(event -> ShowDeviceDetails(event, dev));
        return deviceLabel;
    }

    @FXML
    private void openDevice(ActionEvent event){
        System.out.println("Open own device"+currentDevice);
        try {
                gui.setCurrentDevice(currentDevice);
                gui.setScene("Device", 500, 500);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    @FXML
    private void switchToSharedDevices(){
        this.openDeviceButton = sharedOpenDeviceButton;
        this.DevicesList = sharedDevicesList;
        this.DeviceDetails = sharedDeviceDetails;
        this.DeviceDetailsLabel = sharedDeviceDetalsLabel;
        this.DeviceDetailsListview = sharedDeviceDetalsListview;
    }

    @FXML
    private void switchToOwnDevices(){
        this.openDeviceButton = ownOpenDeviceButton;
        this.DevicesList = myDevicesList;
        this.DeviceDetails = ownDeviceDetails;
        this.DeviceDetailsLabel = ownDeviceDetalsLabel;
        this.DeviceDetailsListview = ownDeviceDetalsListview;
    }

    @FXML
    private void ShowDeviceDetails(MouseEvent event, Device dev){
        logEntriesCount=0;
        Calendar calendar = Calendar.getInstance();
        Trace.out(Trace.Level.DEV, "ShowDevice: " + dev.getName());
        for (Node node : DevicesList.getChildren()) {
            if (node instanceof Label) {
            node.setStyle("-fx-background-color: white; -fx-border-color: Black;");
            }
        }
        ((Label) event.getSource()).setStyle("-fx-background-color: grey; -fx-border-color: Black;");
        DeviceDetails.setVisible(true);
        openDeviceButton.setVisible(true);
        removeDeviceButton.setVisible(true);
        DeviceDetailsLabel.setText(((Label) event.getSource()).getText());
        DeviceDetailsListview.getItems().clear();
        currentDevice = dev;
        List<LogEntry> entries = client.getLogEntries(dev);
        for (int i = 0; i < entries.size(); i++) {
            logEntriesCount++;
            calendar.setTime(entries.get(i).getDate());
            DeviceDetailsListview.getItems().add((calendar.get(Calendar.HOUR_OF_DAY) +":"
            + calendar.get(Calendar.MINUTE) + ":"
            + calendar.get(Calendar.SECOND) + " - "
            + calendar.get(Calendar.DATE) + "/"
            + (calendar.get(Calendar.MONTH) + 1) + "/"
            + calendar.get(Calendar.YEAR)
        )+": "+entries.get(i).getValue());
        }
        logEntriesCountLabel.setText(logEntriesCount+" Log entries");
    }

    private void addNewDevice(MouseEvent event){
        try {
            gui.openPopup("AddDeviceWindow", 300, 350, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogOut(){
        client.logout();
        try {
            gui.setScene("LogSingUp", 300, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemoveDevice(){
        /* TODO: Kinda ugly to send device trough gui but is a quick fix. maybe fix later */
        gui.setCurrentDevice(currentDevice);
        try {
            gui.openPopup("AreYouSurePopup", 300, 190, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: FIX UGLY HACK
    @Override
    public void hook(){
        System.out.println("HOOK");
        devices = client.getDevices(gui.getUser());
        getDevices(myDevicesList, devices);
        ownDeviceDetails.setVisible(false);
        removeDeviceButton.setVisible(false);
        sharedDeviceDetails.setVisible(false);
        sharedOpenDeviceButton.setVisible(false);
        ownOpenDeviceButton.setVisible(false);
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

    private void getDevices(VBox boksi, List<Device> devices){
        boksi.getChildren().clear();
        for (Device device : devices) {
            boksi.getChildren().add(createNewDeviceLabel(device));
        }
        boksi.getChildren().add(addNewDeviceButton());
    }

    @Override
    public void start(){
        devices = client.getDevices(gui.getUser());
        getDevices(myDevicesList, devices);

        /* TODO 
         * client.getDeviceShares()
         * getDevice(DeviceShare.getDeviceId()) saa itse laitteen ulos DeviceSharesta
         * 
         * tässä pitää kattoo miten saa sharedDevices List<Device> muotoon jotta voi sit täyttää sharedDevicesList 
         * Kato samalla sitä miltä shared deviced details näyttää
         * miten Device saa devicen jos se on shared et meneekä DeviceShare???
         * jokaselle shared Device laittaa Device.setOwned(false)
        */
        getDevices(sharedDevicesList, devices);

        ownDeviceDetails.setVisible(false);
        removeDeviceButton.setVisible(false);
        sharedDeviceDetails.setVisible(false);
        sharedOpenDeviceButton.setVisible(false);
        ownOpenDeviceButton.setVisible(false);

        switchToOwnDevices();

        ownDevicesTab.setOnSelectionChanged(e -> {
            System.err.println("Tab changed own devices tab is: "+ownDevicesTab.isSelected());
            if (ownDevicesTab.isSelected()) {
                switchToOwnDevices();
            } else {
                switchToSharedDevices();
            }
        });
        //sharedDevicesTab.setDisable(true);
    }
}
