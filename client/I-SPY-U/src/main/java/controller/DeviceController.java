package controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import model.Device;
import model.LogEntry;
import model.User;

public class DeviceController extends IController {

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML RadioButton radioDaily;
    @FXML RadioButton radioWeekly;
    @FXML RadioButton radioMontly;

    @FXML ChoiceBox<String> actionChoice;
    @FXML ChoiceBox<String> shareChoice;

    @FXML LineChart<String, Number> lineChart;

    private User user;
    private Device device;

    private void setUpCharts() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Calendar calendar = Calendar.getInstance();

        //how to get day, month and year from Date object
        Date date = new Date();
        calendar.setTime(date);
        System.out.println("Date: " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));

        calendar.set(2025, Calendar.FEBRUARY, 10);
        /* TODO: switch from dummy data to actual data */
        for (int i = 0; i < 10; i++) {
            calendar.add(Calendar.DATE, 1);
            series.getData().add(new XYChart.Data<>((calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR)), (Number) i));
        }
    
        lineChart.getData().add(series);
    }

    @Override
    public void start(){
        device = gui.getCurrentDevice();
        List<LogEntry> logs = client.getLogEntries(device);
        // Tarviiko user???? user = gui.getUser();

        radioDaily.setToggleGroup(toggleGroup);
        radioWeekly.setToggleGroup(toggleGroup);
        radioMontly.setToggleGroup(toggleGroup);

        actionChoice.setValue("Select an action");
        shareChoice.setValue("Select a role");

        setUpCharts();
    }
}