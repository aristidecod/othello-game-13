package fr.univ_amu.m1info.board_game_library.controller;

import fr.univ_amu.m1info.board_game_library.ai.MinimaxOthelloAI;
import fr.univ_amu.m1info.board_game_library.ai.OthelloAI;
import fr.univ_amu.m1info.board_game_library.model.Game;
import fr.univ_amu.m1info.board_game_library.view.OthelloView;
import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.model.BoardPosition;
import java.util.List;


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

    private void checkGameOver() {
        // Vérifie s'il y a des coups valides pour l'un ou l'autre des joueurs
        List<BoardPosition> validMovesPlayer1 = game.getGrid().findValidMoves(game.getPlayer1().getColor());
        List<BoardPosition> validMovesPlayer2 = game.getGrid().findValidMoves(game.getPlayer2().getColor());

        if (validMovesPlayer1.isEmpty() && validMovesPlayer2.isEmpty()) {
            // Compte les pions de chaque joueur
            int player1Score = game.getPlayer1().getScore();
            int player2Score = game.getPlayer2().getScore();

            String winner;
            if (player1Score > player2Score) {
                winner = game.getPlayer1().getName();
            } else if (player2Score > player1Score) {
                winner = game.getPlayer2().getName();
            } else {
                winner = "Égalité";
            }

            javafx.application.Platform.runLater(() -> {
                showGameOverDialog(winner);
            });
        }
    }

    private void showGameOverDialog(String winner) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de la partie");
        alert.setHeaderText("Game Over !");

        String message = winner.equals("Égalité") ?
                "Match nul !" :
                "Le gagnant est : " + winner;

        alert.setContentText(message);

        javafx.scene.control.ButtonType newGameButton = new javafx.scene.control.ButtonType("Nouvelle Partie");
        alert.getButtonTypes().setAll(newGameButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == newGameButton) {
                buttonActionOnClick("NewGame");
            }
        });
    }

    public void boardActionOnClick(int row, int column) {
        BoardPosition position = new BoardPosition(row, column);
        if (game.executeMove(position)) {
            othelloView.clearMessages();
            game.switchPlayer();
            updateGameDisplay();

            // Vérifier la fin de partie après chaque coup
            checkGameOver();

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
                    javafx.application.Platform.runLater(() -> {
                        updateGameDisplay();
                        checkGameOver();
                    });
                } else {
                    // L'IA n'a pas trouvé de coup valide
                    javafx.application.Platform.runLater(() -> {
                        game.switchPlayer(); // Retour au joueur humain
                        updateGameDisplay();
                        checkGameOver(); // Vérifie si la partie est terminée
                    });
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
    }

    @Override
    public void buttonActionOnClick(String buttonId) {
        switch (buttonId) {
            case "NewGame":
                game.resetGame();
                updateGameDisplay();
                break;
            case "ShowConsole":
                game.getGrid().displayGrid();
                break;
            case "Undo":
                if (game.canUndo()) {
                    game.undo();
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