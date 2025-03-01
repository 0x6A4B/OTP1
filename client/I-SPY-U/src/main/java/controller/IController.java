package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import model.Client;
import view.GUI;

public abstract class IController {
    protected GUI gui;
    protected Client client = new Client();

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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
