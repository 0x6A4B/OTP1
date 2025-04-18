package controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
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

public class DeviceController extends AbstractController {

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML private RadioButton radioHourly;
    @FXML private RadioButton radioDaily;
    @FXML private RadioButton radioWeekly;

    @FXML private TextField limitMin;
    @FXML private TextField limitMax;
    @FXML private Button setLimitsButton;

    @FXML private Tab configTab;
    @FXML private Tab shareTab;

    @FXML private TextField sharingEmail;
    @FXML private Button shareButton;

    /* @FXML private Label actionItemLabel;
    @FXML private TextField actionInput;
    @FXML private Button setActionButton; */

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

    private Device device;
    private DeviceShare deviceShare = null;
    private boolean editing = false;

    //these are for chanhing text for localization
    @FXML private CategoryAxis chartXAxis;
    @FXML private NumberAxis chartYAxis;
    @FXML private Tab dataTab;
    @FXML private Button backToList;
    @FXML private Label shareLabel;
    @FXML private Label shareEmailLabel;
    @FXML private Label shareRoleLabel;
    @FXML private Label shareDescLabel;

    static final int LOG_ENTRIES_AMOUNT = 30;

    static final int WINDOW_WIDTH = 500;
    static final int WINDOW_HEIGHT = 500;

    private void setUpCharts() {
        //clear the chart if something is resetted
        lineChart.getData().clear();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName(device.getName());
        //get all the log entries
        List<LogEntry> logs = client.getLogEntries(device, LOG_ENTRIES_AMOUNT);
        Trace.out(Trace.Level.DEV, "Loading logentries:");
        for (LogEntry i : logs) {
            Trace.out(Trace.Level.DEV, "\tLogEntry: " + i);
            final XYChart.Data<String, Double> data = new XYChart.Data<>(
                //put date and then the log entry value to the chart
                (localeSingleton.getShortFormattedDateTime(i.getDate())),
                Double.parseDouble(i.getValue()));
                data.setNode(new HoveredThresholdNodea("temperature", localeSingleton.getFormattedTemperature(Double.parseDouble(i.getValue()))));
            series.getData().add(data);
        }
    
        lineChart.getData().add(series);
    }

    @FXML
    private void handleBackButton(){
        try {
            gui.setScene("DevicesList", WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Error in switching scenes: "+e.getMessage());
        }
    }

    //TODO these commented functions are for device config

    /* @FXML
    private void handleSetLimits() {
        System.out.println(limitMin.getText());
        System.out.println(limitMax.getText());
        // TODO tässä laitetaan limitit eteenpäin
        System.out.println("set Limits");
        selectionModel.select(0);
    } */

    /* @FXML
    private void handleActionChoice() {
        String actionString = actionChoice.getValue();
        actionItemLabel.setText(actionString.substring(7, actionString.length())+" for action");
        System.out.println("set Action choice");
    } */

    /* @FXML
    private void handleSetAction() {
        System.out.println(actionChoice.getValue());
        System.out.println(actionInput.getText());
        // TODO tässä laitetaan action eteenpäin
        System.out.println("set Action Happening");
        selectionModel.select(0);
    }
 */
    @FXML
    private void handleShare() {
        Trace.out(Trace.Level.DEV, "sharing Happening");
        Trace.out(Trace.Level.DEV, sharingEmail.getText());
        /* TODO WIP ota shareChoice ja sen mukaan laita sharen permission read/write */

        deviceShare = new DeviceShare();
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
    private void handleEditDesc() {
        //this switched between label and textbox for editing the description
        if (editing) {
            //if editing is true, hide textbox and show label then
            Trace.out(Trace.Level.DEV, "saved desc");
            descLabel.setVisible(true);
            descTextBox.setVisible(false);
            //then get the textboxes text and update share description
            editDescButton.setText(localeSingleton.getTranslation("edit_description"));
            deviceShare.setDescription(descTextBox.getText());
            descLabel.setText(descTextBox.getText());
            descTextBox.clear();
            client.updateDeviceShare(deviceShare);
            editing = false;
        } else {
            //if editing is false, hide label and show textbox then
            Trace.out(Trace.Level.DEV, "edit desc");
            editing = true;
            descLabel.setVisible(false);
            descTextBox.setVisible(true);
            //and set textbox text to the current description
            editDescButton.setText(localeSingleton.getTranslation("Save"));
        }
    }

    //this creates a HBox for each shared user in the list
    //it has a label with the user id and a button to remove the share
    private HBox createShareListBox(String name, DeviceShare share) {
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
        //when the X button is clicked remove from share and refill the list with the new shares
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
            HBox userBox = createShareListBox("Id: "+i.getUserId(), i);
            sharedUsersList.getChildren().add(userBox);
        }
    }

    @Override
    public void translate() {
        Trace.out(Trace.Level.DEV, "Translating");

        backToList.setText(localeSingleton.getTranslation("back_to_devicelist"));

        dataTab.setText(localeSingleton.getTranslation("data"));
        chartLabel.setText(localeSingleton.getTranslation("log_entries"));
        radioDaily.setText(localeSingleton.getTranslation("Daily"));
        radioWeekly.setText(localeSingleton.getTranslation("Weekly"));
        radioHourly.setText(localeSingleton.getTranslation("hourly"));
        chartXAxis.setLabel(localeSingleton.getTranslation("date"));
        chartYAxis.setLabel(localeSingleton.getTranslation("measurement"));

        configTab.setText(localeSingleton.getTranslation("config"));

        shareTab.setText(localeSingleton.getTranslation("share"));
        shareLabel.setText(localeSingleton.getTranslation("sharing"));
        editDescButton.setText(localeSingleton.getTranslation("edit_description"));
        setShareButton.setText(localeSingleton.getTranslation("set"));
        sharedUsersLabel.setText(localeSingleton.getTranslation("device_is_shared_to"));
        shareButton.setText(localeSingleton.getTranslation("share"));
        shareEmailLabel.setText(localeSingleton.getTranslation("email")+":");
        shareRoleLabel.setText(localeSingleton.getTranslation("select_role"));
        shareDescLabel.setText(localeSingleton.getTranslation("description"));

        shareChoice.setValue(localeSingleton.getTranslation("select_role"));
        shareChoice.getItems().clear();
        shareChoice.getItems().addAll(localeSingleton.getTranslation("viewer"), localeSingleton.getTranslation("editor"));
    }

    @Override
    public void start() {
        device = gui.getCurrentDevice();
        radioDaily.setToggleGroup(toggleGroup);
        radioDaily.setSelected(true);
        radioWeekly.setToggleGroup(toggleGroup);
        radioHourly.setToggleGroup(toggleGroup);

        //disable the buttons cause no functionality
        radioDaily.setVisible(false);
        radioWeekly.setVisible(false);
        radioHourly.setVisible(false);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedToggle = (RadioButton) newValue;
                String selectedText = selectedToggle.getText();
                chartLabel.setText(selectedText + " log entries");
                //Tässä vaihtais charttia hourly, daily, weekly charts setUpCharts()
            }
        });

        setLimitsButton.disableProperty().bind(limitMin.textProperty().isEmpty().or(limitMax.textProperty().isEmpty()));
        shareButton.disableProperty().bind(sharingEmail.textProperty().isEmpty().or(descTextBox.textProperty().isEmpty()));

        actionChoice.setValue("Select an action");

        setUpCharts();
        configTab.setDisable(true);

        //here we see if the device is owned by the user or shared with them
        if (device.isOwned()) {
            //if the device is owned by the user, we can share the device
            descLabel.setDisable(true);
            editDescButton.setDisable(true);
            descTextBox.setVisible(true);
            fillSharedUsersList();
        } else {
            //if the device is shared to the user, we disable sharing and enable description editing
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
    }
}
