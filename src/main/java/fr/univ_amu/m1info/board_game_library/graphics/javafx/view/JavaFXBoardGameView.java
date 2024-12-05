package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.bar.Bar;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardGridView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXBoardGameView implements BoardGameControllableView {
    private final Stage stage;
    private BoardGridView boardGridView;
    private Bar bar;
    private BoardGameController controller;

    public void setController(BoardGameController controller) {
        this.controller = controller;
    }

    public JavaFXBoardGameView(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(_ -> Platform.exit());
        stage.setResizable(false);
        stage.sizeToScene();
    }

    public synchronized void reset() {
        VBox vBox = new VBox();
        bar = new Bar();
        boardGridView = new BoardGridView();
        vBox.getChildren().add(bar);
        vBox.getChildren().add(boardGridView);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }

    @Override
    public synchronized void updateLabeledElement(String id, String newText, boolean bold) {
        //bar.updateLabel(id, newText, bold);
        Platform.runLater(() -> bar.updateLabel(id, newText, bold));
    }

    @Override
    public synchronized void setCellColor(int row, int column, Color color) {
        boardGridView.setColorSquare(row, column, color);
    }

    @Override
    public synchronized void addShapeAtCell(int row, int column, Shape shape, Color color) {
        boardGridView.addShapeAtSquare(row, column, shape, color);
    }

    @Override
    public synchronized void removeShapesAtCell(int row, int column) {
        boardGridView.removeShapesAtSquare(row, column);
    }

    public BoardGridView getBoardGridView() {
        return boardGridView;
    }

    public Stage getStage() {
        return stage;
    }

    public Bar getBar() {
        return bar;
    }
    

    public void boardActionOnclick(int row, int column){
        controller.boardActionOnClick(row, column);
    }

    public void buttonActionOnclick(String id){
        controller.buttonActionOnClick(id);
    }

    @Override
    public void setButtonEnabled(String buttonId, boolean enabled){

    }
}
