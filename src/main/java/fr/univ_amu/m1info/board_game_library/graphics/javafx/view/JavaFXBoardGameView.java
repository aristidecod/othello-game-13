package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.bar.Bar;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardGridView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;


public class JavaFXBoardGameView extends VBox implements BoardGameControllableView {
    private final Stage stage;
    private BoardGridView boardGridView;
    private Bar bar;
    private BoardGameController controller;
    private Bar topBar;    // Barre du haut pour les informations
    private Bar bottomBar; // Nouvelle barre du bas pour les boutons

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
        BorderPane mainLayout = new BorderPane();

        // Création des barres
        topBar = new Bar();    // Pour les informations
        bottomBar = new Bar(); // Pour les boutons
        boardGridView = new BoardGridView();

        // Placement des éléments dans le BorderPane
        mainLayout.setTop(topBar);
        mainLayout.setCenter(boardGridView);
        mainLayout.setBottom(bottomBar);

        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
    }

    @Override
    public synchronized void updateLabeledElement(String id, String newText, boolean bold) {
        Platform.runLater(() -> {
            // Vérifier d'abord dans la barre du haut
            if (topBar.containsElement(id)) {
                topBar.updateLabel(id, newText, bold);
            }
            // Puis dans la barre du bas
            else if (bottomBar.containsElement(id)) {
                bottomBar.updateLabel(id, newText, bold);
            }
        });
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

    public Bar getTopBar() {
        return topBar;
    }

    public Bar getBottomBar() {
        return bottomBar;
    }

    public void boardActionOnclick(int row, int column) {
        controller.boardActionOnClick(row, column);
    }

    public void buttonActionOnclick(String id) {
        controller.buttonActionOnClick(id);
    }

    @Override
    public void setButtonEnabled(String buttonId, boolean enabled) {}
}