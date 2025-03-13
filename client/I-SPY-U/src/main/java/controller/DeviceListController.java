package controller;

import java.io.IOException;
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
        /* TODO: miten täs sais datan jos vaikka on vain devcies id?? ja sit jatkaa eteenpäin */
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
            DeviceDetailsListview.getItems().add(entries.get(i).getDate()+": "+entries.get(i).getValue());
        }
    }

    private void addNewDevice(MouseEvent event){
        try {
            gui.openPopup("AddDeviceWindow", 300, 350, this);
            // TODO: Refresh the device window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogOut(){
        /* TODO here we need to clear token client.logout();?????*/
        client.logout();
        try {
            gui.setScene("LogSingUp", 300, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemoveDevice(){
        System.out.println("person clicked remove");
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
        getDevices(myDevicesList);
        getDevices(sharedDevicesList);
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

    private void getDevices(VBox boksi){
        boksi.getChildren().clear();
        /* TODO: tässä pitäis hakee ne devicet ja laittaa ne tohon boksiin
        pitää kattoo jos siihen saa jotenki dictionary tyylisesti
        jotta voidaan saada ehkä id siihen mukaan ja sit se device ikkunalle eteenpäin */
        for (Device device : devices) {
            boksi.getChildren().add(createNewDeviceLabel(device));
        }
        boksi.getChildren().add(addNewDeviceButton());
    }

    @Override
    public void start(){
        devices = client.getDevices(gui.getUser());
        getDevices(myDevicesList);
        getDevices(sharedDevicesList);

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
        sharedDevicesTab.setDisable(true);
    }
}
