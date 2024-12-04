package fr.univ_amu.m1info.board_game_library.controller;

import fr.univ_amu.m1info.board_game_library.model.Game;
import fr.univ_amu.m1info.board_game_library.view.OthelloView;
import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.model.BoardPosition;

public class OthelloController implements BoardGameController {
    private OthelloView othelloView;
    private final Game game;

    public OthelloController() {
        this.game = new Game();
    }

    @Override
    public void initializeViewOnStart(BoardGameView view) {
        this.othelloView = new OthelloView(view);
        game.setView(othelloView);
        othelloView.initializeBoard();
        updateGameDisplay();
        // Mise à jour initiale des scores
        othelloView.updateScores(game.getPlayer1(), game.getPlayer2());
    }

    public void boardActionOnClick(int row, int column) {
        BoardPosition position = new BoardPosition(row, column);
        if (game.executeMove(position)) {
            othelloView.clearMessages();
            game.switchPlayer();
            game.updateScores();
            updateGameDisplay();
        } else {
            othelloView.showInvalidMoveMessage();
        }
    }

    private void updateGameDisplay() {
        othelloView.displayPawns(game.getGrid());
        othelloView.updateCurrentPlayer(game.getCurrentPlayerName());
        othelloView.highlightCells(game.getValidMoves());
        othelloView.updateUndoButton(game.canUndo());
        othelloView.updateRedoButton(game.canRedo());
        othelloView.updateScores(game.getPlayer1(), game.getPlayer2());
    }

    @Override
    public void buttonActionOnClick(String buttonId) {
        switch (buttonId) {
            case "NewGame":
                game.resetGame();
                game.updateScores();
                updateGameDisplay();
                break;
            case "ShowConsole":
                game.getGrid().displayGrid();
                break;
            case "Undo":
                if (game.canUndo()) {
                    game.undo();
                    game.updateScores();
                    updateGameDisplay();
                }
                break;
            case "Redo":
                if (game.canRedo()) {
                    game.redo();
                    game.switchPlayer();
                    game.updateScores();
                    updateGameDisplay();
                }
                break;
        }
    }
}