package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;

import java.util.List;

public class OthelloView {
    private static final int BOARD_SIZE = 8;
    private final BoardGameView view;

    public OthelloView(BoardGameView view) {
        this.view = view;
    }

    public void initializeBoard() {
        BoardIterator iterator = new GridIterator(BOARD_SIZE);
        while (iterator.hasNext()) {
            BoardPosition pos = iterator.next();
            setCheckerboardPattern(pos.getRow(), pos.getCol());
        }
    }

    public void displayPawns(Grid grid) {
        clearBoard();
        BoardIterator iterator = new GridIterator(BOARD_SIZE);
        while (iterator.hasNext()) {
            BoardPosition pos = iterator.next();
            Pawn pawn = grid.getPawn(pos.getRow(), pos.getCol());
            if (pawn != null) {
                Color pieceColor = pawn.getColor() == PlayerColor.BLACK ? Color.BLACK : Color.WHITE;
                view.addShapeAtCell(pos.getRow(), pos.getCol(), Shape.CIRCLE, pieceColor);
            }
        }
    }

    public void highlightCells(List<int[]> positions) {
        for (int[] position : positions) {
            view.setCellColor(position[0], position[1], Color.LIGHTBLUE);
        }
    }

    public void updateCurrentPlayer(String playerName) {
        view.updateLabeledElement("currentPlayerLabel", "Current Player: " + playerName, true);
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
            view.removeShapesAtCell(pos.getRow(), pos.getCol());
        }
    }
}