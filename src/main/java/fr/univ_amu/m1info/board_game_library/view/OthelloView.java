package fr.univ_amu.m1info.board_game_library.view;

import fr.univ_amu.m1info.board_game_library.model.*;
import fr.univ_amu.m1info.board_game_library.iterator.GridIterator;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class OthelloView {
    private static final int BOARD_SIZE = 8;
    private final BoardGameView view;
    private final List<BoardPosition> currentHighlightedCells;
    private BoardPosition lastPlayedPosition;

    public OthelloView(BoardGameView view) {
        this.view = view;
        this.currentHighlightedCells = new ArrayList<>();
        this.lastPlayedPosition = null;
    }

    public void initializeBoard() {
        Iterator<BoardPosition> iterator = new GridIterator(BOARD_SIZE);
        while (iterator.hasNext()) {
            BoardPosition pos = iterator.next();
            setCheckerboardPattern(pos.row(), pos.col());
        }
    }

    public void updateUndoButton(boolean canUndo) {
        view.setButtonEnabled("Undo", canUndo);
    }

    public void updateRedoButton(boolean canRedo) {
        view.setButtonEnabled("Redo", canRedo);
    }

    public void displayPawns(Grid grid) {
        clearBoard();
        Iterator<BoardPosition> iterator = new GridIterator(BOARD_SIZE);
        while (iterator.hasNext()) {
            BoardPosition position = iterator.next();
            Pawn pawn = grid.getPawn(position);
            if (pawn != null) {
                currentHighlightedCells.remove(position);
                setCheckerboardPattern(position.row(), position.col());

                // D'abord placer le pion principal
                Color pieceColor = switch (pawn.getColor()) {
                    case BLACK -> Color.BLACK;
                    case WHITE -> Color.WHITE;
                };

                // Créer un nouveau Group ou StackPane pour chaque cellule
                view.clearCell(position.row(), position.col());  // Vous devrez ajouter cette méthode
                view.addShapeAtCell(position.row(), position.col(), Shape.CIRCLE, pieceColor);

                // Ensuite ajouter le point rouge si c'est la dernière position jouée
                if (lastPlayedPosition != null &&
                        position.row() == lastPlayedPosition.row() &&
                        position.col() == lastPlayedPosition.col()) {
                    view.addShapeAtCellWithSize(position.row(), position.col(),
                            Shape.CIRCLE, Color.RED, 0.15);
                }
            }
        }
    }

    public void resetLastPlayedPosition() {
        lastPlayedPosition = null;
    }

    public void markLastPlayedPosition(BoardPosition position) {
        lastPlayedPosition = position;
        if (position != null) {
            view.addShapeAtCellWithSize(position.row(), position.col(),
                    Shape.CIRCLE, Color.LIGHT_RED, 0.2);
        }
    }

    public void highlightCells(List<BoardPosition> newPositions) {
        // Supprime les anciens indicateurs
        for (BoardPosition position : currentHighlightedCells) {
            setCheckerboardPattern(position.row(), position.col());
            view.removeShapesAtCell(position.row(), position.col());
        }
        currentHighlightedCells.clear();

        // Ajoute les nouveaux indicateurs et met à jour la liste
        for (BoardPosition position : newPositions) {
            setCheckerboardPattern(position.row(), position.col());
            view.addShapeAtCellWithSize(position.row(), position.col(), Shape.CIRCLE, Color.TRANSPARENT_LIGHTBLUE, 0.3);
            currentHighlightedCells.add(position);
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
        view.updateLabeledElement("Info", "      Invalid Move", true);
    }

    public void showAIStatusMessage(boolean aiEnabled) {
        view.updateLabeledElement("Info", aiEnabled ? "           IA activée" : "      IA désactivée", true);
    }

    public void clearMessages() {
        view.updateLabeledElement("Info", "", true);
    }

    private void setCheckerboardPattern(int row, int col) {
        view.setCellColor(row, col, Color.GREEN);
    }

    private void clearBoard() {
        Iterator<BoardPosition> iterator = new GridIterator(BOARD_SIZE);
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
                    game.resetGame();
                    displayPawns(game.getGrid());
                    updateCurrentPlayer(game.getCurrentPlayerName());
                    highlightCells(game.getValidMoves());
                    updateUndoButton(game.canUndo());
                }
            });
        });
    }
}