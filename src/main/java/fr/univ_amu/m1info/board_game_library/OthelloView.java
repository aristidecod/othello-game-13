package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;

import java.util.ArrayList;
import java.util.List;

public class OthelloView {
    private static final int BOARD_SIZE = 8;
    private final BoardGameView view;
    private List<int[]> currentHighlightedCells;

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
            BoardPosition pos = iterator.next();
            Pawn pawn = grid.getPawn(pos.row(), pos.col());
            if (pawn != null) {
                Color pieceColor = pawn.getColor() == PlayerColor.BLACK ? Color.BLACK : Color.WHITE;
                view.addShapeAtCell(pos.row(), pos.col(), Shape.CIRCLE, pieceColor);
            }
        }
    }

    public void highlightCells(List<int[]> newPositions) {
        for (int[] position : currentHighlightedCells) {
            setCheckerboardPattern(position[0], position[1]);
        }
        currentHighlightedCells = new ArrayList<>(newPositions);
        for (int[] position : newPositions) {
            view.setCellColor(position[0], position[1], Color.LIGHTBLUE);
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
}