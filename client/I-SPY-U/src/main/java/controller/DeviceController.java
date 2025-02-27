package controller;

import java.util.Calendar;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.Device;
import model.LogEntry;
import model.User;

public class DeviceController extends IController {

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML RadioButton radioDaily;
    @FXML RadioButton radioWeekly;
    @FXML RadioButton radioMontly;

    @FXML private TextField limitMin;
    @FXML private TextField limitMax;

    @FXML private TextField sharingEmail;

    @FXML private Label actionItemLabel;
    @FXML private TextField actionInput;

    @FXML ChoiceBox<String> actionChoice;
    @FXML ChoiceBox<String> shareChoice;

    @FXML LineChart<String, String> lineChart;

    private User user;
    private Device device;

    private void setUpCharts() {
        XYChart.Series<String, String> series = new XYChart.Series<>();
        Calendar calendar = Calendar.getInstance();
        List<LogEntry> logs = client.getLogEntries(device);
        for (LogEntry i : logs) {
            calendar.setTime(i.getDate());
            series.getData().add(new XYChart.Data<>((calendar.get(Calendar.SECOND)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.HOUR)+" - "+calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR)), i.getValue().substring(0, 6)));
        }
    
        lineChart.getData().add(series);
    }

    @FXML
    private void handleSetLimits() {
        System.out.println(limitMin.getText());
        System.out.println(limitMax.getText());
        /* TODO tässä laitetaan limitit eteenpäin */
        System.out.println("set Limits");
    }

    @FXML
    private void handleActionChoice() {
        System.out.println(actionChoice.getValue());
        actionItemLabel.setText(actionChoice.getValue()+" for action");
        System.out.println("set Action choice");
    }

    @FXML
    private void handleSetAction() {
        System.out.println(actionChoice.getValue());
        System.out.println(actionInput.getText());
        /* TODO tässä laitetaan action eteenpäin */
        System.out.println("set Action Happening");
    }

    @FXML
    private void handleShare() {
        System.out.println(sharingEmail.getText());
        /* TODO tässä laitetaan sharing eteenpäin */
        System.out.println("sharing Happening");
    }

    @FXML
    private void handleSetShare() {
        System.out.println(shareChoice.getValue());
        /* TODO tässä laitetaan sharing settings eteenpäin */
        System.out.println("sharing settings Happening");
    }

    @Override
    public void start(){
        device = gui.getCurrentDevice();
        // Tarviiko user???? user = gui.getUser();

        radioDaily.setToggleGroup(toggleGroup);
        radioWeekly.setToggleGroup(toggleGroup);
        radioMontly.setToggleGroup(toggleGroup);

        actionChoice.setValue("Select an action");
        shareChoice.setValue("Select a role");

        setUpCharts();
    }
}