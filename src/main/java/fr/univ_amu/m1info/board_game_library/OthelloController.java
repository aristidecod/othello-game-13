package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;

public class OthelloController implements BoardGameController {
    private OthelloView othelloView;
    private final GameLogic gameLogic;

    public OthelloController() {
        this.gameLogic = new GameLogic();
    }

    @Override
    public void initializeViewOnStart(BoardGameView view) {
        this.othelloView = new OthelloView(view);
        othelloView.initializeBoard();
        updateGameDisplay();
    }

    @Override
    public void boardActionOnClick(int row, int column) {
        if (gameLogic.makeMove(row, column)) {
            othelloView.clearMessages();
           // gameLogic.switchPlayer();
            updateGameDisplay();
        } else {
            othelloView.showInvalidMoveMessage();
        }
    }


    private void updateGameDisplay() {
        othelloView.displayPawns(gameLogic.getGrid());
        othelloView.updateCurrentPlayer(gameLogic.getCurrentPlayerName());
        othelloView.highlightCells(gameLogic.getValidMoves());
        othelloView.updateUndoButton(gameLogic.canUndo());
    }

    @Override
    public void buttonActionOnClick(String buttonId) {
        switch (buttonId) {
            case "NewGame":
                gameLogic.resetGame();
                updateGameDisplay();
                break;
            case "ShowConsole":
                gameLogic.getGrid().displayGrid();
                break;
            case "Undo":
                if (gameLogic.canUndo()) {
                    gameLogic.undo();
                    updateGameDisplay();
                }
                break;
        }
    }
}