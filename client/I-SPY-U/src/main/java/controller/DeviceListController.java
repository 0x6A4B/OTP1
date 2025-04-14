package controller;

import java.util.ArrayList;
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
import model.DeviceShare;
import model.LogEntry;
import util.Trace;

public class DeviceListController extends AbstractController {

    private VBox devicesList;
    private VBox deviceDetails;
    private Label deviceDetailsLabel;
    private Label logEntriesCountLabel;
    private ListView<String> deviceDetailsListview;
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
    @FXML private Button removeShareButton;
    @FXML private Button removeButton;

    @FXML private Label ownDeviceDetalsLabel;
    @FXML private Label ownLogEntriesCountLabel;
    @FXML private ListView<String> ownDeviceDetalsListview;

    @FXML private Label sharedDeviceDetalsLabel;
    @FXML private Label shareLogEntriesCountLabel;
    @FXML private ListView<String> sharedDeviceDetalsListview;

    private List<Device> devices;
    private List<Device> sharedDevices = new ArrayList<>();
    private Device currentDevice;

    private int logEntriesCount = 0;
    private List<DeviceShare> shares;

    private Label newDeviceLabelSaver = addNewDeviceButton();

    //these are for chanching text for localization
    @FXML private Label ownDevicesLogTitle;
    @FXML private Label sharedDevicesLogTitle;
    @FXML private Button logOutButton;

    static final int LOGSING_WINDOW_WIDTH = 300;
    static final int LOGSING_WINDOW_HEIGHT = 400;

    static final int POPUP_WINDOW_WIDTH = 300;
    static final int POPUP_WINDOW_HEIGHT = 190;

    static final int ADD_WINDOW_WIDTH = 300;
    static final int ADD_WINDOW_HEIGHT = 350;

    static final int DEVICE_WINDOW_WIDTH = 500;
    static final int DEVICE_WINDOW_HEIGHT = 500;

    private Label createNewDeviceLabel(Device dev) {
        Label deviceLabel = new Label(dev.getName());
        deviceLabel.setAlignment(Pos.CENTER);
        deviceLabel.setContentDisplay(ContentDisplay.CENTER);
        deviceLabel.setPrefHeight(30.0);
        deviceLabel.setPrefWidth(185.0);
        deviceLabel.setStyle("-fx-border-color: Black;");
        deviceLabel.setOnMouseClicked(event -> showDeviceDetails(event, dev));
        return deviceLabel;
    }

    @FXML
    private void openDevice(ActionEvent event) {
        Trace.out(Trace.Level.DEV, "Open own device"+currentDevice);
        try {
            gui.setCurrentDevice(currentDevice);
            gui.setScene("Device", DEVICE_WINDOW_WIDTH, DEVICE_WINDOW_HEIGHT);
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Error switching scenes: "+e.getMessage());
        }
    }

    @FXML
    //here we switch variables we use to shared ones, when user switched to share tab
    private void switchToSharedDevices() {
        this.openDeviceButton = sharedOpenDeviceButton;
        this.devicesList = sharedDevicesList;
        this.deviceDetails = sharedDeviceDetails;
        this.deviceDetailsLabel = sharedDeviceDetalsLabel;
        this.deviceDetailsListview = sharedDeviceDetalsListview;
        this.logEntriesCountLabel = shareLogEntriesCountLabel;
        this.removeButton = removeShareButton;
    }

    @FXML
    //here we switch variables we use to owned ones, when user switched to own tab

    private void switchToOwnDevices() {
        this.openDeviceButton = ownOpenDeviceButton;
        this.devicesList = myDevicesList;
        this.deviceDetails = ownDeviceDetails;
        this.deviceDetailsLabel = ownDeviceDetalsLabel;
        this.deviceDetailsListview = ownDeviceDetalsListview;
        this.logEntriesCountLabel = ownLogEntriesCountLabel;
        this.removeButton = removeDeviceButton;
    }

    @FXML
    //this happenns when user clicks on the device label in the list of devices
    private void showDeviceDetails(MouseEvent event, Device dev) {
        //first we set everything up like count to 0 and calendar
        logEntriesCount = 0;
        Calendar calendar = Calendar.getInstance();
        Trace.out(Trace.Level.DEV, "ShowDevice: " + dev.getName());
        //then we set all devices background white and text black,
        for (Node node : devicesList.getChildren()) {
            if (node instanceof Label) {
            node.setStyle("-fx-background-color: white; -fx-border-color: Black;");
            }
        }
        //and then set the chosen devices background to grey to highlight it being chose
        ((Label) event.getSource()).setStyle("-fx-background-color: grey; -fx-border-color: Black;");
        deviceDetails.setVisible(true);
        openDeviceButton.setVisible(true);
        removeButton.setVisible(true);
        deviceDetailsLabel.setText(((Label) event.getSource()).getText());
        deviceDetailsListview.getItems().clear();
        currentDevice = dev;
        //get all all entries for the device
        List<LogEntry> entries = client.getLogEntries(dev);
        //go trought all the log entires and add them to the listview, also count them to show how many the device has in total
        for (int i = 0; i < entries.size(); i++) {
            logEntriesCount++;
            calendar.setTime(entries.get(i).getDate());
            deviceDetailsListview.getItems().add(localeSingleton.getShortFormattedDateTime(entries.get(i).getDate()) + ": " + localeSingleton.getFormattedTemperature(Double.parseDouble(entries.get(i).getValue())));
        }
        logEntriesCountLabel.setText(logEntriesCount + " " + localeSingleton.getTranslation("log_entries"));
    }

    private void addNewDevice(MouseEvent event) {
        try {
            gui.openPopup("AddDeviceWindow", ADD_WINDOW_WIDTH, ADD_WINDOW_HEIGHT, this);
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Error switching scenes: "+e.getMessage());
        }
    }

    @FXML
    private void handleLogOut() {
        client.logout();
        try {
            gui.setScene("LogSingUp", LOGSING_WINDOW_WIDTH, LOGSING_WINDOW_HEIGHT);
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Error switching scenes: "+e.getMessage());
            
        }
    }

    @FXML
    private void handleRemoveDevice() {
        /* TODO: Kinda ugly to send device trough gui but is a quick fix. maybe fix later */
        gui.setCurrentDevice(currentDevice);
        try {
            gui.openPopup("AreYouSurePopup", POPUP_WINDOW_WIDTH, POPUP_WINDOW_HEIGHT, this);
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Error switching scenes: "+e.getMessage());
        }
    }

    // TODO: FIX UGLY HACK
    @Override
    public void hook() {
        Trace.out(Trace.Level.DEV, "HOOK");
        devices = client.getDevices(gui.getUser());
        getDevices(myDevicesList, devices, true);
        ownDeviceDetails.setVisible(false);
        removeDeviceButton.setVisible(false);
        sharedDeviceDetails.setVisible(false);
        sharedOpenDeviceButton.setVisible(false);
        ownOpenDeviceButton.setVisible(false);
    }

    //this creates a label that when clicked, opens new device window
    //it is used in the list of devices, so user can add new device to his list
    //it is always at the bottom of the list
    private Label addNewDeviceButton() {
        Label newdeviceLabel = new Label("+ " + localeSingleton.getTranslation("add_new_device"));
        newdeviceLabel.setAlignment(Pos.CENTER);
        newdeviceLabel.setContentDisplay(ContentDisplay.CENTER);
        newdeviceLabel.setPrefHeight(30.0);
        newdeviceLabel.setPrefWidth(185.0);
        newdeviceLabel.setStyle("-fx-border-color: grey;");
        newdeviceLabel.setOnMouseClicked(this :: addNewDevice);
        return newdeviceLabel;
    }

    private void getDevices(VBox boksi, List<Device> devices, boolean isOwn) {
        boksi.getChildren().clear();
        for (Device device : devices) {
            boksi.getChildren().add(createNewDeviceLabel(device));
        }
        if (isOwn) {
            boksi.getChildren().add(newDeviceLabelSaver);
        }
    }

    @FXML
    private void handleRemoveShare() {
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
    public void translate() {
        Trace.out(Trace.Level.DEV, "Translating");

        logOutButton.setText(localeSingleton.getTranslation("logout"));

        sharedDevicesTab.setText(localeSingleton.getTranslation("share_devices"));
        sharedDevicesLogTitle.setText(localeSingleton.getTranslation("last_log"));
        removeShareButton.setText(localeSingleton.getTranslation("remove_share"));
        sharedOpenDeviceButton.setText(localeSingleton.getTranslation("open_device"));

        ownDevicesTab.setText(localeSingleton.getTranslation("my_devices"));
        ownDevicesLogTitle.setText(localeSingleton.getTranslation("last_log"));
        removeDeviceButton.setText(localeSingleton.getTranslation("delete_device"));
        ownOpenDeviceButton.setText(localeSingleton.getTranslation("open_device"));
        try {
            logEntriesCountLabel.setText(logEntriesCount + " " + localeSingleton.getTranslation("log_entries"));
        } catch (Exception e) {
            //gotta catch exception dont know what to do with it
        }
        newDeviceLabelSaver.setText("+ " + localeSingleton.getTranslation("add_new_device"));
    }

    @Override
    public void start() {
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
            Trace.out(Trace.Level.DEV, "Tab changed own devices tab is: " + ownDevicesTab.isSelected());
            if (ownDevicesTab.isSelected()) {
                switchToOwnDevices();
            } else {
                switchToSharedDevices();
            }
        });
    }
}
