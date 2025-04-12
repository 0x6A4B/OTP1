package controller;

import java.util.List;

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
import util.Trace;
import view.GUI;

public abstract class AbstractController {
    protected GUI gui;
    protected Client client = new Client();
    @FXML protected AnchorPane mainBoio;
    @FXML protected ComboBox<String> languageDropdown;
    LocaleSingleton localeSingleton = LocaleSingleton.getInstance();

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        List<Stage> stages = Window.getWindows().stream()
            .filter(Stage.class :: isInstance)
            .map(Stage.class :: cast)
            .toList();
        for (Stage stage : stages) {
            stage.close();
        }
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void start() {
        Trace.out(Trace.Level.DEV, "Starting");
    }

    public void translate() {
        Trace.out(Trace.Level.DEV, "Translating");
        //this we fill in separate controllers, and where we switch the text in labels and such
    }

    // TODO: FIX THIS UGLY HACK
    public void hook() {}

    public void setLanguage() {
        localeSingleton.setLocale(localeSingleton.getAvailableLocales().get(languageDropdown.getSelectionModel().getSelectedIndex()));
        Trace.out(Trace.Level.DEV, "Language set to: " + localeSingleton.getLocale());
        if (localeSingleton.isRightToLeft()) {
            mainBoio.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            Trace.out(Trace.Level.DEV, "Right to left");
        } else {
            mainBoio.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            Trace.out(Trace.Level.DEV, "Left to right");
        }
    }

    @FXML
    public void initialize() {
        localeSingleton.getAvailableLocales().forEach(l -> languageDropdown.getItems().add(localeSingleton.getLanguageName(l)));
        Trace.out(Trace.Level.DEV, String.valueOf(localeSingleton.getLocale()));
        Trace.out(Trace.Level.DEV, String.valueOf(localeSingleton.getAvailableLocales().indexOf(localeSingleton.getLocale())));
        languageDropdown.getSelectionModel().select(localeSingleton.getAvailableLocales().indexOf(localeSingleton.getLocale()));
        languageDropdown.setPrefWidth(54);
        languageDropdown.setOnAction(event -> {
            setLanguage();
            translate();
        });


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
            Trace.out(Trace.Level.DEV, "Right to left");
        } else {
            mainBoio.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            Trace.out(Trace.Level.DEV, "Left to right");
        }
        translate();
    }
}
