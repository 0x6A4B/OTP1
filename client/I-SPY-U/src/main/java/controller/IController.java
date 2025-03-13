package controller;

import java.util.List;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Client;
import view.GUI;
import javafx.stage.Window;

public abstract class IController {
    protected GUI gui;
    protected Client client = new Client();

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        List<Stage> stages = Window.getWindows().stream()
            .filter(Stage.class::isInstance)
            .map(Stage.class::cast)
            .collect(Collectors.toList());
        for (Stage stage : stages) {
            stage.close();
        }
    }
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
    public void setClient(Client client) {
        //this.client = client;
    }
    public void start(){
        System.out.println("Starting");
    };

    // TODO: FIX THIS UGLY HACK
    public void hook(){}
}
