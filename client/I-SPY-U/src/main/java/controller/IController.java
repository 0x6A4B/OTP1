package controller;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Client;
import util.LocaleSingleton;
import view.GUI;

public abstract class IController {
    protected GUI gui;
    protected Client client = new Client();
    @FXML protected AnchorPane mainBoio;
    @FXML protected ComboBox<String> languageDropdown;
    LocaleSingleton localeSingleton = LocaleSingleton.getInstance();

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

    public void trainslate(){
        //this we fill in separate controllers, and where we switch the text in labels and such
    }

    // TODO: FIX THIS UGLY HACK
    public void hook(){}

    @FXML
    public void initialize() {
        /* 
         * TODO
         * Tee joku funktio joka tapahtuu ku languageDropdownissa valitaan uus, joka sit tekee localeSingleton setlocale
         * samalla se sit tekee translate funktion jotka pitää täyttää jokaisessa kontrollerissa
         * tehä viel joku et translate runataan initializessa kattoen
         */
        localeSingleton.getAvailableLocales().forEach(l -> languageDropdown.getItems().add(l.getDisplayLanguage()));
        languageDropdown.getSelectionModel().select(1);
        languageDropdown.setPrefWidth(54);
        System.out.println(languageDropdown.getSelectionModel().getSelectedItem());

        //this sets the button to always show globe emoji
        StringProperty fixedText = new SimpleStringProperty("🌐");
        languageDropdown.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(fixedText.get());
                }
            }
        });
        if (localeSingleton.isRightToLeft()) {
            mainBoio.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            mainBoio.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
    }
}
