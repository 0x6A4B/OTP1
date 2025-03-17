package view;

import controller.AltController;
import controller.IAltController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Device;
import model.DeviceShare;
import model.LogEntry;
import util.LocaleSingleton;
import util.Trace;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AltView extends Application {
    private IAltController controller;

    private ObservableList<Device> devices = FXCollections.observableArrayList();
    private ObservableList<LogEntry> logEntries = FXCollections.observableArrayList();
    private ObservableList<DeviceShare> shares = FXCollections.observableArrayList();

    private TableView<Device> deviceTableView;
    private TableView<LogEntry> logTableView;
    private TableView<DeviceShare> sharesTableView;

    // Save scenes here?
    Map<String, Scene> scenes = new HashMap<>();

    Stage stage;
    Scene activeScene;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        controller = new AltController(this);
        stage.setTitle("Alt");




        /* Init scenes */

        loadScene("Login");


    }

    /* Splash */
    private void splash(){}
    /* Login */
    private void sceneLogin(){
        VBox root = new VBox();

        Label title = new Label(LocaleSingleton.getInstance().getTranslation("login"));

        HBox usernameBox = new HBox();
        HBox passwordBox = new HBox();

        Label username = new Label(LocaleSingleton.getInstance().getTranslation("username"));
        Label password = new Label(LocaleSingleton.getInstance().getTranslation("Password"));
        Label errorMessage = new Label();
        errorMessage.setVisible(false);

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        usernameBox.getChildren().addAll(username, usernameField);
        passwordBox.getChildren().addAll(password, passwordField);

        Button loginButton = new Button(LocaleSingleton.getInstance().getTranslation("Login"));

        loginButton.setOnAction(event -> {
            String response = controller.login(usernameField.getText(), passwordField.getText());
            if (!response.equals("OK")){
                //ERROR
                System.out.println("ERROR LOGIN: " + response);
                errorMessage.setText(response);
                errorMessage.setVisible(true);
                return;
            }
            // success
            loadScene("MainScene");

        });

        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPadding(new Insets(20, 20, 20, 20));

        root.getChildren().addAll(title, usernameBox, passwordBox, loginButton, errorMessage);

        addScene("Login", new Scene(root, 300, 400));
    }
    /* Signup */
    private void sceneSignup(){

    }
    /* Devices scenes */
    /* Main scene */
    private void sceneDevices(){
        VBox root = new VBox();
        Label title = new Label(LocaleSingleton.getInstance().getTranslation("Devices"));

        //HBox toolBar = new HBox();
        ToolBar toolBar = new ToolBar();

        Button addDevice = new Button(LocaleSingleton.getInstance().getTranslation("add_device"));
        Button deleteDevice = new Button(LocaleSingleton.getInstance().getTranslation("Delete_Device"));
        Button editDevice = new Button(LocaleSingleton.getInstance().getTranslation("Edit Device"));
        Button shareDevice = new Button(LocaleSingleton.getInstance().getTranslation("Share Device"));

        Button viewShares = new Button(LocaleSingleton.getInstance().getTranslation("View Shares"));
        Button viewDevice = new Button(LocaleSingleton.getInstance().getTranslation("View Device"));
        Button logOut = new Button(LocaleSingleton.getInstance().getTranslation("Log Out"));
        toolBar.getItems().addAll(addDevice, deleteDevice, editDevice, shareDevice, new Separator(),
                viewShares, viewDevice, new Separator(), logOut);

        HBox mainBox = new HBox();
        HBox bottomBox = new HBox();    // What could we use this for?...

        deviceTableView = new TableView<>();
        logTableView = new TableView<>();

        /* Size */
        //deviceTableView.setPrefSize(stage.getWidth()*0.5, stage.getHeight()*0.5);
        //deviceTableView.prefHeightProperty().bind(mainBox.heightProperty().multiply(0.7));
        deviceTableView.prefWidthProperty().bind(mainBox.widthProperty().multiply(0.5));

        logTableView.prefWidthProperty().bind(mainBox.widthProperty().multiply(0.5));

        //toolBar.prefHeightProperty().bind(mainBox.heightProperty().multiply(0.7));
        mainBox.prefHeightProperty().bind(stage.heightProperty().multiply(1));




        /* Columns */
        TableColumn<Device, String> deviceNameColumn = new TableColumn<>(LocaleSingleton.getInstance().getTranslation("Name"));
        deviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deviceNameColumn.setMinWidth(150);
        TableColumn<Device, String> deviceDescColumn = new TableColumn<>(LocaleSingleton.getInstance().getTranslation("Description"));
        deviceDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        deviceNameColumn.setMinWidth(250);
        TableColumn<Device, String> deviceModelColumn = new TableColumn<>(LocaleSingleton.getInstance().getTranslation("Model"));
        deviceModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        deviceNameColumn.setMinWidth(150);


        TableColumn<LogEntry, Date> logEntryDateColumn = new TableColumn<>(LocaleSingleton.getInstance().getTranslation("Date"));
        logEntryDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        logEntryDateColumn.setCellFactory(col -> {
            TableCell<LogEntry, Date> cell = new TableCell<>() {
                //private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                //private DateTimeFormatter dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(LocaleSingleton.getInstance().getLocale());
                //private DateTimeFormatter dateFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.LONG);
                //private DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, LocaleSingleton.getInstance().getLocale());
                private SimpleDateFormat dateFormat = new SimpleDateFormat(
                        ((SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG)).toLocalizedPattern()

                        , LocaleSingleton.getInstance().getLocale());


                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    }else {
                        setText(dateFormat.format(item));
                    }
                }
            };
            return cell;
        });
        logEntryDateColumn.setMinWidth(150);
        //TableColumn<LogEntry, String> logEntryTimeColumn = new TableColumn<>("Time");
        TableColumn<LogEntry, String> logEntryKeyColumn = new TableColumn<>("Key");
        logEntryKeyColumn.setCellValueFactory(new PropertyValueFactory<>("logkey"));
        logEntryDateColumn.setMinWidth(150);
        TableColumn<LogEntry, String> logEntryValueColumn = new TableColumn<>("Value");
        logEntryValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        logEntryDateColumn.setMinWidth(150);

        deviceTableView.getColumns().addAll(deviceNameColumn, deviceDescColumn, deviceModelColumn);
        logTableView.getColumns().addAll(logEntryDateColumn, logEntryKeyColumn, logEntryValueColumn);


        deviceTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    refreshLogEntries();
                });



        /* Events */
        refreshDevices();

        deviceTableView.setItems(devices);
        logTableView.setItems(logEntries);


        mainBox.getChildren().addAll(deviceTableView, logTableView);

        root.getChildren().addAll(title, toolBar, mainBox);


        addScene("MainScene", new Scene(root, 850, 600));

    }
    private void sceneDevice(){

    }
    /* Shares views */
    private void sceneDeviceShares(){

    }
    private void sceneDeviceShare(){

    }


    private void loadScene(String sceneString){
        if (scenes.containsKey(sceneString)){
            activeScene = scenes.get(sceneString);
            stage.setScene(activeScene);
            stage.show();
            return;
        }
        // ERROR
        switch (sceneString){
            case "MainScene" -> sceneDevices();
            case "Devices" -> sceneDevices();
            case "Device" -> sceneDevice();

            case "Login" -> sceneLogin();
            case "Signup" -> sceneSignup();

            case "Shares" -> sceneDeviceShares();
            case "Share" -> sceneDeviceShare();
        }
        loadScene(sceneString);
    }

    private void addScene(String key, Scene scene){
        if(!scenes.containsKey(key))
            scenes.put(key, scene);
    }

    /* Handling of the lists */
    private void refreshDevices(){
        Trace.out(Trace.Level.DEV, "Refresh devices");
        devices.clear();
        devices.addAll(controller.getDevices());
    }
    private void refreshLogEntries(){
        logEntries.clear();
        logEntries.addAll(controller.getLogEntries(deviceTableView.getSelectionModel().getSelectedItem()));
    }
    private void refreshShares(){
        shares.clear();
        shares.addAll(controller.getDeviceShares());
    }
}
