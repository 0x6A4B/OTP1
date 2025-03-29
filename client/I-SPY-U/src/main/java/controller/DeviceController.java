package controller;

import java.util.Calendar;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Device;
import model.DeviceShare;
import model.LogEntry;
import model.User;
import util.Trace;

public class DeviceController extends IController {

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML private RadioButton radioDaily;
    @FXML private RadioButton radioWeekly;
    @FXML private RadioButton radioHourly;

    @FXML private TextField limitMin;
    @FXML private TextField limitMax;
    @FXML private Button setLimitsButton;

    @FXML private Tab configTab;
    @FXML private Tab shareTab;

    @FXML private TextField sharingEmail;
    @FXML private Button shareButton;

    @FXML private Label actionItemLabel;
    @FXML private TextField actionInput;
    @FXML private Button setActionButton;

    @FXML private ChoiceBox<String> actionChoice;

    @FXML private ChoiceBox<String> shareChoice;
    @FXML private Button setShareButton;
    @FXML private Label sharedUsersLabel;

    @FXML private Label chartLabel;
    @FXML private LineChart<String, Double> lineChart;

    @FXML private VBox sharedUsersList;
    @FXML private ScrollPane sharedUsersListContainer;

    @FXML private Label descLabel;
    @FXML private TextArea descTextBox;
    @FXML private Button editDescButton;

    private SingleSelectionModel<Tab> selectionModel;
    @FXML private TabPane tabPane;

    private User user;
    private Device device;
    private DeviceShare deviceShare = null;
    private Boolean editing = false;

    //these are for chanhing text for localization
    @FXML private Tab dataTab;
    @FXML private Label shareLabel;
    @FXML private Label shareEmailLabel;
    @FXML private Label shareRoleLabel;
    @FXML private Label shareDescLabel;

    private void setUpCharts() {
        lineChart.getData().clear();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName(device.getName());
        Calendar calendar = Calendar.getInstance();
        List<LogEntry> logs = client.getLogEntries(device, 30);
        Trace.out(Trace.Level.DEV, "Loading logentries:");
        for (LogEntry i : logs) {
            Trace.out(Trace.Level.DEV, "\tLogEntry: " + i);
            calendar.setTime(i.getDate());
            final XYChart.Data<String, Double> data = new XYChart.Data<>(
                (calendar.get(Calendar.HOUR_OF_DAY) +":"
                        + calendar.get(Calendar.MINUTE) + ":"
                        + calendar.get(Calendar.SECOND) + " - "
                        + calendar.get(Calendar.DATE) + "/"
                        + (calendar.get(Calendar.MONTH) + 1) + "/"
                        + calendar.get(Calendar.YEAR)
                ),
                Double.parseDouble(i.getValue())/*.substring(0, 6)*/);
                data.setNode(new HoveredThresholdNodea("temperature", i.getValue()));
            series.getData().add(data);
        }
    
        lineChart.getData().add(series);
    }

    @FXML
    private void handleBackButton(){
        try {
            gui.setScene("DevicesList", 500, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSetLimits() {
        System.out.println(limitMin.getText());
        System.out.println(limitMax.getText());
        /* TODO tässä laitetaan limitit eteenpäin */
        System.out.println("set Limits");
        selectionModel.select(0);
    }

    @FXML
    private void handleActionChoice() {
        String actionString = actionChoice.getValue();
        actionItemLabel.setText(actionString.substring(7, actionString.length())+" for action");
        System.out.println("set Action choice");
    }

    @FXML
    private void handleSetAction() {
        System.out.println(actionChoice.getValue());
        System.out.println(actionInput.getText());
        /* TODO tässä laitetaan action eteenpäin */
        System.out.println("set Action Happening");
        selectionModel.select(0);
    }

    @FXML
    private void handleShare() {
        System.out.println("sharing Happening");
        System.out.println(sharingEmail.getText());
        /* TODO WIP ota shareChoice ja sen mukaan laita sharen permission read/write */

        DeviceShare deviceShare = new DeviceShare();
        User user = new User(sharingEmail.getText(), "");
        sharingEmail.clear();

        deviceShare.setUser(user);
        deviceShare.setDevice(device);
        deviceShare.setDescription(descTextBox.getText());
        descTextBox.clear();

        client.shareDevice(deviceShare); /* TODO tässä pitää kattoo jos on null nii laittaa error message share napin vieree vaikka "User not found" */

        fillSharedUsersList();
    }

    @FXML
    private void handleSetShare() {
        System.out.println(shareChoice.getValue());
        /* TODO tässä laitetaan sharing settings eteenpäin */
        System.out.println("sharing settings Happening");
        selectionModel.select(0);
    }

    @FXML
    private void handleEditDesc() {
        if (editing) {
            System.out.println("saved desc");
            descLabel.setVisible(true);
            descTextBox.setVisible(false);
            editDescButton.setText("Edit");
            deviceShare.setDescription(descTextBox.getText());
            descLabel.setText(descTextBox.getText());
            descTextBox.clear();
            client.updateDeviceShare(deviceShare);
            editing = false;
        } else {
            System.out.println("edit desc");
            editing = true;
            descLabel.setVisible(false);
            descTextBox.setVisible(true);
            editDescButton.setText("Save");
        }
    }

    private HBox makeTheBox(String name, DeviceShare share){
        HBox hbox = new HBox();
        hbox.setPrefHeight(25.0);
        hbox.setPrefWidth(200.0);

        Label label = new Label(name);
        label.setMaxHeight(26.0);
        label.setMinHeight(26.0);
        label.setPrefHeight(26.0);
        label.setPrefWidth(165.0);
        label.setStyle("-fx-border-color: grey;");
        label.setPadding(new Insets(0, 0, 0, 10.0));

        Button button = new Button("X");
        button.setStyle("-fx-background-color: darkred;");
        button.setTextFill(Color.WHITE);
        button.setOnAction(e -> {
            client.removeDeviceShare(share);
            fillSharedUsersList();
        });

        hbox.getChildren().addAll(label, button);
        return hbox;
    }

    private void fillSharedUsersList() {
        sharedUsersList.getChildren().clear();
        List<DeviceShare> shares = client.getDeviceShares(device);
        for (DeviceShare i : shares) {
            HBox userBox = makeTheBox("Id: "+i.getUserId(), i);
            sharedUsersList.getChildren().add(userBox);
        }
    }

    @Override
    public void start(){
        device = gui.getCurrentDevice();
        // Tarviiko user???? user = gui.getUser();
        radioDaily.setToggleGroup(toggleGroup);
        radioDaily.setSelected(true);
        radioWeekly.setToggleGroup(toggleGroup);
        radioHourly.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedToggle = (RadioButton) newValue;
                String selectedText = selectedToggle.getText();
                chartLabel.setText(selectedText + " log entries");
                //Tässä vaihtais charttia hourly, daily, weekly charts setUpCharts();
            }
        });

        setLimitsButton.disableProperty().bind(limitMin.textProperty().isEmpty().or(limitMax.textProperty().isEmpty()));
        shareButton.disableProperty().bind(sharingEmail.textProperty().isEmpty().or(descTextBox.textProperty().isEmpty()));

        actionChoice.setValue("Select an action");
        shareChoice.setValue("Select a role");

        selectionModel = tabPane.getSelectionModel();
        setUpCharts();
        configTab.setDisable(true);
        //shareTab.setDisable(true); working on this now

        if (device.isOwned()) {
            descLabel.setDisable(true);
            editDescButton.setDisable(true);
            descTextBox.setVisible(true);
            fillSharedUsersList();
        } else {
            List<DeviceShare> shares = client.getDeviceShares();
            for (DeviceShare share : shares) {
                if (share.getDeviceId() == device.getId()) {
                    descLabel.setText(share.getDescription());
                    descTextBox.setText(share.getDescription());
                    descTextBox.setVisible(false);
                    deviceShare = share;
                    break;
                }
            }
            shareChoice.setDisable(true);
            setShareButton.setDisable(true);
            sharingEmail.setDisable(true);
            sharedUsersList.setVisible(false);
            sharedUsersListContainer.setVisible(false);
            sharedUsersLabel.setVisible(false);
        }
        //mirrorUI();
    }
}