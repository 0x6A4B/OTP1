package controller;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

class HoveredThresholdNodea extends StackPane {

    public HoveredThresholdNodea(String string, Object object) {
        setPrefSize(15, 15);

        final Label label = createDataThresholdLabel(string, object);

        setOnMouseEntered(mouseEvent -> {
            getChildren().setAll(label);
            setCursor(Cursor.NONE);
            toFront();
        });
        setOnMouseExited(mouseEvent -> {
            getChildren().clear();
        });
    }

    private Label createDataThresholdLabel(String string, Object object) {
        final Label label = new Label(object + "");
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        System.out.println(string);
        if (string.equals("engine1")) {
            label.setTextFill(Color.RED);
            label.setStyle("-fx-border-color: RED;");
        } else if (string.equals("engine2")) {
            label.setTextFill(Color.ORANGE);
            label.setStyle("-fx-border-color: ORANGE;");
        } else {
            label.setTextFill(Color.GREEN);
            label.setStyle("-fx-border-color: GREEN;");
        }

        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }
}
