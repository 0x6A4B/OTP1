package controller;

import java.io.IOException;

import javafx.fxml.FXML;

public class AreYouSureController extends IController {
    
    @FXML
    private void handleRemoveDevice(){
        System.out.println("person clicked remove on are you sure");
        client.removeDevice(gui.getCurrentDevice());
        try {
            gui.closePopup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
