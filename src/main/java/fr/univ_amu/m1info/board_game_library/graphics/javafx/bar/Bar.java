package fr.univ_amu.m1info.board_game_library.graphics.javafx.bar;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;


import java.util.HashMap;
import java.util.Map;

public class Bar extends GridPane {
    private final Map<String, Labeled> labeledElements = new HashMap<>();
    private final Map<String, Button> buttons = new HashMap<>();

    public Bar() {
        super();
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);
    }

    public void addLabel(String id, String initialText, int row, int column, int rowSpan, int columnSpan) {
        if(labeledElements.containsKey(id)) {
            throw new IllegalArgumentException("Label " + id + " already exists");
        }

        Label label = new Label(initialText);
        label.setAlignment(Pos.BASELINE_CENTER);

        if (id.equals("Info")) {
            label.setId("Info");
        } else if (id.equals("player1Score") || id.equals("player2Score")) {
            label.setId(id);
        }

        labeledElements.put(id, label);
        add(label, column, row, columnSpan, rowSpan);
    }

    public void setButtonAction(String id, ButtonActionOnClick buttonActionOnClick){
       buttons.get(id).setOnAction(_ -> buttonActionOnClick.onClick());
    }

    public void addButton(String id, String label, int row, int column, int rowSpan, int columnSpan) {
        Button button = new Button(label);
        button.getStyleClass().add("button");
        button.setId(id);
        labeledElements.put(id, button);
        buttons.put(id, button);
        add(button, column, row, columnSpan, rowSpan);
    }

    public void updateLabel(String id, String newText, boolean bold) {
        if(labeledElements.containsKey(id)) {
            Labeled element = labeledElements.get(id);
            element.setText(newText);
            if(bold) {
                element.getStyleClass().add("bold-text");
            } else {
                element.getStyleClass().remove("bold-text");
            }
        }
    }

    public boolean containsElement(String id) {
        return labeledElements.containsKey(id);
    }
}
