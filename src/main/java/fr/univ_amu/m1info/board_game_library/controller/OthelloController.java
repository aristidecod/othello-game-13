package fr.univ_amu.m1info.board_game_library.controller;

import fr.univ_amu.m1info.board_game_library.ai.MinimaxOthelloAI;
import fr.univ_amu.m1info.board_game_library.ai.OthelloAI;
import fr.univ_amu.m1info.board_game_library.model.Game;
import fr.univ_amu.m1info.board_game_library.view.OthelloView;
import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.model.BoardPosition;

public class OthelloController implements BoardGameController {
    private OthelloView othelloView;
    private final Game game;
    private final OthelloAI ai;
    private boolean aiEnabled = false;

    public OthelloController() {
        this.game = new Game();
        this.ai = new MinimaxOthelloAI();
    }

    @Override
    public void initializeViewOnStart(BoardGameView view) {
        this.othelloView = new OthelloView(view);
        game.setView(othelloView);
        othelloView.initializeBoard();
        updateGameDisplay();
        othelloView.updateScores(game.getPlayer1(), game.getPlayer2());
    }

    public void setAiEnabled(boolean enabled) {
        this.aiEnabled = enabled;
    }

    public void boardActionOnClick(int row, int column) {
        BoardPosition position = new BoardPosition(row, column);
        if (game.executeMove(position)) {
            othelloView.clearMessages();
            game.switchPlayer();
            game.updateScores();
            updateGameDisplay();

            if (aiEnabled && game.getCurrentPlayer() == game.getPlayer2()) {
                makeAIMove();
            }
        } else {
            othelloView.showInvalidMoveMessage();
        }
    }

    private void makeAIMove() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
                BoardPosition aiMove = ai.findBestMove(game, game.getCurrentPlayer().getColor());
                if (aiMove != null) {
                    game.executeMove(aiMove);
                    game.switchPlayer();
                    javafx.application.Platform.runLater(this::updateGameDisplay);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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
            case "AIToggle":
                setAiEnabled(!aiEnabled);
                othelloView.showMessage(aiEnabled ? "IA activée" : "IA désactivée");
                break;
        }
    }
}