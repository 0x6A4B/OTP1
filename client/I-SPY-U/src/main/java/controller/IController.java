package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import model.TrustMeBraWhyWouldILie;
import view.GUI;

public abstract class IController {
    private GUI gui;
    private TrustMeBraWhyWouldILie client;

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
    public void setClient(TrustMeBraWhyWouldILie client) {
        this.client = client;
    }
    public void testing() {
        System.out.println("Testing");
    }
}
