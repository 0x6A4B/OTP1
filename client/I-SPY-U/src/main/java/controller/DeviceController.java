package controller;

import java.util.Calendar;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.Device;
import model.LogEntry;
import model.User;
import util.Trace;

public class DeviceController extends IController {

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML RadioButton radioDaily;
    @FXML RadioButton radioWeekly;
    @FXML RadioButton radioHourly;

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

    @FXML ChoiceBox<String> actionChoice;

    @FXML ChoiceBox<String> shareChoice;
    @FXML private Button setShareButton;

    @FXML Label chartLabel;
    @FXML LineChart<String, Double> lineChart;

    private SingleSelectionModel<Tab> selectionModel;
    @FXML TabPane tabPane;

    private User user;
    private Device device;

    private void setUpCharts() {
        lineChart.getData().clear();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName(device.getName());
        Calendar calendar = Calendar.getInstance();
        List<LogEntry> logs = client.getLogEntries(device);
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
        System.out.println(sharingEmail.getText());
        /* TODO tässä laitetaan sharing eteenpäin */
        System.out.println("sharing Happening");
        selectionModel.select(0);
    }

    @FXML
    private void handleSetShare() {
        System.out.println(shareChoice.getValue());
        /* TODO tässä laitetaan sharing settings eteenpäin */
        System.out.println("sharing settings Happening");
        selectionModel.select(0);
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
        shareButton.disableProperty().bind(sharingEmail.textProperty().isEmpty());

        actionChoice.setValue("Select an action");
        shareChoice.setValue("Select a role");

        selectionModel = tabPane.getSelectionModel();
        setUpCharts();
        configTab.setDisable(true);
        shareTab.setDisable(true);
    }
}