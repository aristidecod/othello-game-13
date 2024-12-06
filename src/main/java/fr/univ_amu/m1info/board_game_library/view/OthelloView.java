package fr.univ_amu.m1info.board_game_library.view;

import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.iterator.GridIterator;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import fr.univ_amu.m1info.board_game_library.iterator.BoardIterator;
import fr.univ_amu.m1info.board_game_library.model.BoardPosition;

import java.util.ArrayList;
import java.util.List;

public class OthelloView {
    private static final int BOARD_SIZE = 8;
    private final BoardGameView view;
    private List<BoardPosition> currentHighlightedCells;

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

    public void showMessage(String message) {
        view.updateLabeledElement("Info", message, false);
    }

    /*public void styleGameOverDialog(javafx.scene.control.Alert alert) {
        javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("""
        -fx-background-color: #34495e;
        -fx-text-fill: white;
        """);

        dialogPane.lookupButton(javafx.scene.control.ButtonType.OK).setStyle("""
        -fx-background-color: #3498db;
        -fx-text-fill: white;
        -fx-font-weight: bold;
        """);
    }*/
}