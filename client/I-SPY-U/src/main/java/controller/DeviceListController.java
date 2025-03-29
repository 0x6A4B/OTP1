package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Device;
import model.DeviceShare;
import model.LogEntry;
import util.Trace;

public class DeviceListController extends IController {

    private VBox DevicesList;
    private VBox DeviceDetails;
    private Label DeviceDetailsLabel;
    private ListView DeviceDetailsListview;
    private Button openDeviceButton;
    private Label logEntriesCountLabel;

    @FXML private VBox myDevicesList;
    @FXML private VBox sharedDevicesList;

    @FXML private Tab ownDevicesTab;
    @FXML private Tab sharedDevicesTab;

    @FXML private VBox sharedDeviceDetails;
    @FXML private VBox ownDeviceDetails;

    @FXML private Button sharedOpenDeviceButton;
    @FXML private Button ownOpenDeviceButton;

    @FXML private Button removeDeviceButton;
    @FXML private Button removeShareButton;
    private Button removeButton;

    @FXML private Label ownDeviceDetalsLabel;
    @FXML private ListView ownDeviceDetalsListview;
    @FXML private Label ownLogEntriesCountLabel;

    @FXML private Label sharedDeviceDetalsLabel;
    @FXML private ListView sharedDeviceDetalsListview;
    @FXML private Label shareLogEntriesCountLabel;

    private List<Device> devices;
    private List<Device> sharedDevices = new ArrayList<Device>();
    private Device currentDevice;

    private int logEntriesCount = 0;
    private List<DeviceShare> shares;

    private Label newDeviceLabelSaver = addNewDeviceButton();

    //these are for chanhing text for localization
    @FXML private Label ownDevicesLogTitle;
    @FXML private Label sharedDevicesLogTitle;

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
        this.logEntriesCountLabel = shareLogEntriesCountLabel;
        this.removeButton = removeShareButton;
    }

    @FXML
    private void switchToOwnDevices(){
        this.openDeviceButton = ownOpenDeviceButton;
        this.DevicesList = myDevicesList;
        this.DeviceDetails = ownDeviceDetails;
        this.DeviceDetailsLabel = ownDeviceDetalsLabel;
        this.DeviceDetailsListview = ownDeviceDetalsListview;
        this.logEntriesCountLabel = ownLogEntriesCountLabel;
        this.removeButton = removeDeviceButton;
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
        removeButton.setVisible(true);
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
        logEntriesCountLabel.setText(logEntriesCount+" "+localeSingleton.getTranslation("log_entries"));
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
        getDevices(myDevicesList, devices, true);
        ownDeviceDetails.setVisible(false);
        removeDeviceButton.setVisible(false);
        sharedDeviceDetails.setVisible(false);
        sharedOpenDeviceButton.setVisible(false);
        ownOpenDeviceButton.setVisible(false);
    }

    private Label addNewDeviceButton(){
        Label newdeviceLabel = new Label("+ "+localeSingleton.getTranslation("add_new_device"));
        newdeviceLabel.setAlignment(Pos.CENTER);
        newdeviceLabel.setContentDisplay(ContentDisplay.CENTER);
        newdeviceLabel.setPrefHeight(30.0);
        newdeviceLabel.setPrefWidth(185.0);
        newdeviceLabel.setStyle("-fx-border-color: grey;");
        newdeviceLabel.setOnMouseClicked(this::addNewDevice);
        return newdeviceLabel;
    }

    private void getDevices(VBox boksi, List<Device> devices, Boolean IsOwn){
        boksi.getChildren().clear();
        for (Device device : devices) {
            boksi.getChildren().add(createNewDeviceLabel(device));
        }
        if (IsOwn) {
            boksi.getChildren().add(newDeviceLabelSaver);
        }
    }

    @FXML
    private void handleRemoveShare(){
        for (DeviceShare share : shares) {
            if (share.getDeviceId() == currentDevice.getId()) {
                client.removeDeviceShare(share);
                break;
            }
        }
        sharedDevices.remove(currentDevice);
        getDevices(sharedDevicesList, sharedDevices, false);
        sharedDeviceDetails.setVisible(false);
        sharedOpenDeviceButton.setVisible(false);
        removeShareButton.setVisible(false);
    }

    @Override
    public void translate(){
        //TODO check if this all works, kinda buggin when all translations are empty
        System.out.println("Translating");
        
        sharedDevicesTab.setText(localeSingleton.getTranslation("share_devices"));
        sharedDevicesLogTitle.setText(localeSingleton.getTranslation("last_log"));
        removeShareButton.setText(localeSingleton.getTranslation("remove_share"));
        sharedOpenDeviceButton.setText(localeSingleton.getTranslation("open_device"));

        ownDevicesTab.setText(localeSingleton.getTranslation("my_devices"));
        ownDevicesLogTitle.setText(localeSingleton.getTranslation("last_log"));
        removeDeviceButton.setText(localeSingleton.getTranslation("delete_device"));
        ownOpenDeviceButton.setText(localeSingleton.getTranslation("open_device"));
        try {
            logEntriesCountLabel.setText(logEntriesCount+" "+localeSingleton.getTranslation("log_entries"));
        } catch (Exception e) {
        }

        newDeviceLabelSaver.setText("+ "+localeSingleton.getTranslation("add_new_device"));
    }

    @Override
    public void start(){
        devices = client.getDevices(gui.getUser());
        getDevices(myDevicesList, devices, true);
        shares = client.getDeviceShares();
        //Device theDevice; does declaring it here make it faster than having Device theDevice = something; in the for loop?
        for (DeviceShare share : shares) {
            Device theDevice = client.getDevice(share.getDeviceId());
            theDevice.setOwned(false);
            sharedDevices.add(theDevice);
        }
        getDevices(sharedDevicesList, sharedDevices, false);

        ownDeviceDetails.setVisible(false);
        ownOpenDeviceButton.setVisible(false);
        removeDeviceButton.setVisible(false);

        sharedDeviceDetails.setVisible(false);
        sharedOpenDeviceButton.setVisible(false);
        removeShareButton.setVisible(false);

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

        //mirrorUI();
    }
}
