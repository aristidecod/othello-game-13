package fr.univ_amu.m1info.board_game_library.controller;

import fr.univ_amu.m1info.board_game_library.ai.MinimaxOthelloAI;
import fr.univ_amu.m1info.board_game_library.ai.OthelloAI;
import fr.univ_amu.m1info.board_game_library.model.Game;
import fr.univ_amu.m1info.board_game_library.view.OthelloView;
import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.model.BoardPosition;
import javafx.application.Platform;

public class OthelloController implements BoardGameController {
    private OthelloView othelloView;
    private final Game game;
    private final OthelloAI ai;
    private boolean aiEnabled = false;
    private boolean isAITurn = false;

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
        if(isAITurn) {
            return;
        }

        BoardPosition position = new BoardPosition(row, column);
        if (game.executeMove(position)) {
            othelloView.clearMessages();
            othelloView.markLastPlayedPosition(position);
            updateGameDisplay();  // Mise à jour immédiate de l'affichage

            // Utilisation de Platform.runLater pour laisser l'UI se mettre à jour
            Platform.runLater(() -> {
                if (game.isGameOver()) {
                    handleGameOver();
                    return;
                }

                game.switchPlayer();
                game.updateScores();
                updateGameDisplay();

                if (game.getValidMoves().isEmpty()) {
                    if (game.isGameOver()) {
                        handleGameOver();
                    } else {
                        game.switchPlayer();
                        updateGameDisplay();
                    }
                }

                if (aiEnabled && game.getCurrentPlayer() == game.getPlayer2()) {
                    makeAIMove();
                }
            });
        } else {
            othelloView.showInvalidMoveMessage();
        }
    }

    private void handleGameOver() {
        String winner;
        int player1Score = game.getPlayer1().getScore();
        int player2Score = game.getPlayer2().getScore();

        if (player1Score > player2Score) {
            winner = game.getPlayer1().getName();
        } else if (player1Score < player2Score) {
            winner = game.getPlayer2().getName();
        } else {
            winner = "Égalité";
        }

        othelloView.showGameOverDialog(game, winner, player1Score, player2Score);
    }

    private void makeAIMove() {
        isAITurn = true;
        new Thread(() -> {
            BoardPosition aiMove = ai.findBestMove(game, game.getCurrentPlayer().getColor());

            Platform.runLater(() -> {
                if (aiMove != null) {
                    game.executeMove(aiMove);
                    othelloView.markLastPlayedPosition(aiMove);
                    updateGameDisplay();

                    Platform.runLater(() -> {
                        if (game.isGameOver()) {
                            handleGameOver();
                            return;
                        }

                        game.switchPlayer();
                        updateGameDisplay();

                        if (game.getValidMoves().isEmpty()) {
                            if (game.isGameOver()) {
                                handleGameOver();
                            } else {
                                game.switchPlayer();
                                updateGameDisplay();
                            }
                        }
                        isAITurn = false;
                    });
                }
            });
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
                othelloView.resetLastPlayedPosition();
                game.updateScores();
                updateGameDisplay();
                break;
            case "Undo":
                if (game.canUndo()) {
                    if (aiEnabled) {
                        game.undo();
                        game.undo();
                        BoardPosition lastPos = game.getLastPlayedPosition();
                        othelloView.markLastPlayedPosition(lastPos);
                    } else {
                        game.undo();
                        BoardPosition lastPos = game.getLastPlayedPosition();
                        othelloView.markLastPlayedPosition(lastPos);
                    }
                    game.updateScores();
                    updateGameDisplay();
                }
                break;
            case "Redo":
                if (game.canRedo()) {
                    if (aiEnabled) {
                        game.redo();
                        game.switchPlayer();
                        game.redo();
                        game.switchPlayer();
                        BoardPosition lastPos = game.getLastPlayedPosition();
                        othelloView.markLastPlayedPosition(lastPos);
                    } else {
                        game.redo();
                        game.switchPlayer();
                        BoardPosition lastPos = game.getLastPlayedPosition();
                        othelloView.markLastPlayedPosition(lastPos);
                    }
                    game.updateScores();
                    updateGameDisplay();
                }
                break;
            case "AIToggle":
                game.resetGame();
                setAiEnabled(!aiEnabled);
                othelloView.showAIStatusMessage(aiEnabled);
                othelloView.resetLastPlayedPosition();
                updateGameDisplay();
                break;
        }
    }
}