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
        gameLogic.setView(othelloView); // Ajout de cette ligne
        othelloView.initializeBoard();
        updateGameDisplay();
        // Mise à jour initiale des scores
        othelloView.updateScores(gameLogic.getPlayer1(), gameLogic.getPlayer2());
    }

    @Override
    public void boardActionOnClick(int row, int column) {
        if (gameLogic.executeMove(row, column)) {
            othelloView.clearMessages();
            gameLogic.switchPlayer();
            updateGameDisplay();
            // Les scores sont maintenant mis à jour automatiquement via GameLogic.updateScores()
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