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
        if(labeledElements.containsKey(id)){
            throw new IllegalArgumentException("Label " + id + " already exists");
        }
        Label label = new Label(initialText);
        label.setAlignment(Pos.BASELINE_CENTER);
        labeledElements.put(id, label);
        add(label, column, row, columnSpan, rowSpan);
    }

    public void setButtonAction(String id, ButtonActionOnClick buttonActionOnClick){
       buttons.get(id).setOnAction(_ -> buttonActionOnClick.onClick());
    }

    public void addButton(String id, String label, int row, int column, int rowSpan, int columnSpan) {
        Button button = new Button(label);
        labeledElements.put(id, button);
        buttons.put(id, button);
        add(button, column, row, columnSpan, rowSpan);
    }

    public void updateLabel(String id, String newText, boolean bold){
        if(labeledElements.containsKey(id)){
            labeledElements.get(id).setText(newText);
            if(bold){
                labeledElements.get(id).setStyle("-fx-font-weight: bold");
            }
        }
    }
    /* Méthodes de compatibilité pour l'ancien code
    public void addLabel(String id, String initialText) {
        addLabel(id, initialText, 0, 0, 1, 1);
    }

    public void addButton(String id, String label) {
        addButton(id, label, 0, 0, 1, 1);
    }*/
}
