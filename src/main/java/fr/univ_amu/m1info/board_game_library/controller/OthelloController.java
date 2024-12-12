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

            if (game.isGameOver()) {
                handleGameOver();
                return;
            }

            game.switchPlayer();
            game.updateScores();
            updateGameDisplay();

            // Si pas de coups possibles pour le joueur suivant
            if (game.getValidMoves().isEmpty()) {
                if (game.isGameOver()) {
                    handleGameOver();
                } else {
                    game.switchPlayer();
                    updateGameDisplay();
                }
            }

            // Gestion de l'IA après toutes les vérifications
            if (aiEnabled && game.getCurrentPlayer() == game.getPlayer2()) {
                makeAIMove();
            }
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
        new Thread(() -> {
            try {
                Thread.sleep(500);
                BoardPosition aiMove = ai.findBestMove(game, game.getCurrentPlayer().getColor());

                javafx.application.Platform.runLater(() -> {
                    if (aiMove != null) {
                        game.executeMove(aiMove);

                        if (game.isGameOver()) {
                            handleGameOver();
                            return;
                        }

                        game.switchPlayer();
                        updateGameDisplay();

                        // Vérification supplémentaire pour le joueur suivant
                        if (game.getValidMoves().isEmpty()) {
                            if (game.isGameOver()) {
                                handleGameOver();
                            } else {
                                game.switchPlayer();
                                updateGameDisplay();
                            }
                        }
                    }
                });

            } catch (InterruptedException e) {
                System.err.println("Error during AI move: " + e.getMessage());
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
            case "Undo":
                if (game.canUndo()) {
                    if (aiEnabled) {
                        game.undo();
                        game.undo();
                    } else {
                        game.undo();
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
                    } else {
                        game.redo();
                    }
                    game.switchPlayer();
                    game.updateScores();
                    updateGameDisplay();
                }
                break;
            case "AIToggle":
                game.resetGame();
                aiEnabled = !aiEnabled;
                othelloView.showAIStatusMessage(aiEnabled);
                updateGameDisplay();
                break;
        }
    }
}