package fr.univ_amu.m1info.board_game_library.view;

import fr.univ_amu.m1info.board_game_library.model.*;
import fr.univ_amu.m1info.board_game_library.iterator.GridIterator;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import fr.univ_amu.m1info.board_game_library.iterator.BoardIterator;
import fr.univ_amu.m1info.board_game_library.controller.OthelloController;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;

import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class OthelloView {
    private static final int BOARD_SIZE = 8;
    private final BoardGameView view;
    private List<BoardPosition> currentHighlightedCells;
    private OthelloController controller;
    private Button newGameButton;

    public void setController(OthelloController controller) {
        this.controller = controller;
    }

    public OthelloView(BoardGameView view) {
        this.view = view;
        this.currentHighlightedCells = new ArrayList<>();
    }

    public void initializeBoard() {
        BoardIterator iterator = new GridIterator(BOARD_SIZE);
        while (iterator.hasNext()) {
            BoardPosition pos = iterator.next();
            setCheckerboardPattern(pos.row(), pos.col());
        }
    }

    public void updateUndoButton(boolean canUndo) {
        view.setButtonEnabled("Undo", canUndo);
    }

    public void displayPawns(Grid grid) {
        clearBoard();
        BoardIterator iterator = new GridIterator(BOARD_SIZE);
        while (iterator.hasNext()) {
            BoardPosition position = iterator.next();
            Pawn pawn = grid.getPawn(position);
            if (pawn != null) {
                Color pieceColor = switch (pawn.getColor()) {
                    case BLACK -> Color.BLACK;
                    case WHITE -> Color.WHITE;
                };
                view.addShapeAtCell(position.row(), position.col(), Shape.CIRCLE, pieceColor);
            }
        }
    }

    public void highlightCells(List<BoardPosition> newPositions) {
        for (BoardPosition position : currentHighlightedCells) {
            setCheckerboardPattern(position.row(), position.col());
        }
        currentHighlightedCells = new ArrayList<>(newPositions);
        for (BoardPosition position : newPositions) {
            view.setCellColor(position.row(), position.col(), Color.LIGHTBLUE);
        }
    }

    public void updateCurrentPlayer(String playerName) {
        view.updateLabeledElement("currentPlayerLabel", "Current Player: " + playerName, true);
    }

    public void updateScores(Player player1, Player player2) {
        view.updateLabeledElement("player1Score",
                player1.getName() + ": " + player1.getScore(), true);
        view.updateLabeledElement("player2Score",
                player2.getName() + ": " + player2.getScore(), true);
    }

    public void showInvalidMoveMessage() {
        view.updateLabeledElement("Info", "Invalid Move", true);
    }

    public void clearMessages() {
        view.updateLabeledElement("Info", "", true);
    }

    private void setCheckerboardPattern(int row, int col) {
        boolean isEven = (row + col) % 2 == 0;
        view.setCellColor(row, col, isEven ? Color.GREEN : Color.LIGHTGREEN);
    }

    private void clearBoard() {
        BoardIterator iterator = new GridIterator(BOARD_SIZE);
        while (iterator.hasNext()) {
            BoardPosition pos = iterator.next();
            view.removeShapesAtCell(pos.row(), pos.col());
        }
    }

    public void showGameOverDialog(Game game, String winner, int scoreNoirs, int scoreBlancs) {
        Platform.runLater(() -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Partie terminée !");
            dialog.setHeaderText(null);

            VBox content = new VBox(10);
            content.setAlignment(Pos.CENTER);

            Label titleLabel = new Label("Partie terminée !");
            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            Label scoreLabel = new Label("Score final :");
            Label noirLabel = new Label(String.format("Noirs : %d", scoreNoirs));
            Label blancLabel = new Label(String.format("Blancs : %d", scoreBlancs));

            Label winnerLabel = new Label(
                    scoreNoirs > scoreBlancs ? winner + "a gagné" :
                            scoreBlancs > scoreNoirs ?  winner + "a gagné" :
                                    "Match nul !"
            );
            winnerLabel.setStyle("-fx-font-weight: bold;");

            content.getChildren().addAll(
                    titleLabel,
                    new Separator(),
                    scoreLabel,
                    noirLabel,
                    blancLabel,
                    new Separator(),
                    winnerLabel
            );

            ButtonType newGameButton = new ButtonType("Nouvelle partie", ButtonBar.ButtonData.OK_DONE);
            ButtonType closeButton = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(newGameButton, closeButton);

            String buttonStyle =
                    "-fx-background-color: #4CAF50; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 8px 16px; " +
                            "-fx-border-radius: 4px; " +
                            "-fx-cursor: hand;";

            dialog.getDialogPane().lookupButton(newGameButton).setStyle(buttonStyle);
            dialog.getDialogPane().lookupButton(closeButton).setStyle(buttonStyle);

            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().setStyle("-fx-background-color: white; -fx-padding: 20px;");

            dialog.showAndWait().ifPresent(response -> {
                if (response == newGameButton) {
                    // view.fireEventNewGame();
                    game.resetGame();
                    displayPawns(game.getGrid());
                    updateCurrentPlayer(game.getCurrentPlayerName());
                    highlightCells(game.getValidMoves());
                    updateUndoButton(game.canUndo());
                }
            });
        });
    }

    public void showMessage(String message) {
        view.updateLabeledElement("Info", message, false);
    }

}