package fr.univ_amu.m1info.board_game_library.controller;

import fr.univ_amu.m1info.board_game_library.ai.MinimaxOthelloAI;
import fr.univ_amu.m1info.board_game_library.ai.OthelloAI;
import fr.univ_amu.m1info.board_game_library.model.Game;
import fr.univ_amu.m1info.board_game_library.view.OthelloView;
import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.model.BoardPosition;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import fr.univ_amu.m1info.board_game_library.model.Player;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
        Stage gameOverStage = new Stage(StageStyle.TRANSPARENT);
        gameOverStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("""
        -fx-background-color: rgba(0,0,0,0.8);
        -fx-background-radius: 20;
        """);

        // Titre
        Label titleLabel = new Label("Game Over");
        titleLabel.setStyle("""
        -fx-font-size: 48px;
        -fx-text-fill: #e74c3c;
        -fx-font-weight: bold;
        """);

        // Animation du titre
        ScaleTransition pulseAnimation = new ScaleTransition(Duration.seconds(2), titleLabel);
        pulseAnimation.setFromX(1.0);
        pulseAnimation.setFromY(1.0);
        pulseAnimation.setToX(1.1);
        pulseAnimation.setToY(1.1);
        pulseAnimation.setCycleCount(Animation.INDEFINITE);
        pulseAnimation.setAutoReverse(true);
        pulseAnimation.play();

        // Scores
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        player1.calculateScore(game.getGrid());
        player2.calculateScore(game.getGrid());

        VBox scoresBox = new VBox(5);
        scoresBox.setAlignment(Pos.CENTER);
        Label scoreLabel1 = new Label("Noir: " + player1.getScore());
        Label scoreLabel2 = new Label("Blanc: " + player2.getScore());
        scoresBox.getChildren().addAll(scoreLabel1, scoreLabel2);
        scoresBox.setStyle("""
        -fx-font-size: 24px;
        -fx-text-fill: white;
        """);

        // Gagnant
        Label winnerLabel = new Label("Le gagnant est : " + winner);
        winnerLabel.setStyle("""
        -fx-font-size: 32px;
        -fx-text-fill: #f1c40f;
        """);

        // Stickers
        Label stickersLabel = new Label("🏆 🎮 ⭐ 🎉");
        stickersLabel.setStyle("-fx-font-size: 40px;");

        // Animation des stickers
        TranslateTransition bounceAnimation = new TranslateTransition(Duration.seconds(1), stickersLabel);
        bounceAnimation.setFromY(0);
        bounceAnimation.setToY(-20);
        bounceAnimation.setCycleCount(Animation.INDEFINITE);
        bounceAnimation.setAutoReverse(true);
        bounceAnimation.play();

        // Message de félicitations
        Label congratsLabel = new Label("Félicitations au vainqueur !");
        congratsLabel.setStyle("""
        -fx-font-size: 28px;
        -fx-text-fill: #2ecc71;
        """);

        // Bouton Nouvelle Partie
        Button newGameButton = new Button("Nouvelle Partie");
        newGameButton.setStyle("""
        -fx-background-color: #3498db;
        -fx-text-fill: white;
        -fx-font-size: 18px;
        -fx-padding: 10 20;
        -fx-background-radius: 5;
        """);

        newGameButton.setOnAction(e -> {
            game.resetGame();
            gameOverStage.close();
        });

        // Ajout de tous les éléments
        root.getChildren().addAll(
                titleLabel,
                scoresBox,
                winnerLabel,
                stickersLabel,
                congratsLabel,
                newGameButton
        );

        // Animation d'entrée
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        Scene scene = new Scene(root);
        scene.setFill(null);
        gameOverStage.setScene(scene);

        // Rendre la fenêtre déplaçable
        root.setOnMousePressed(e -> {
            root.setOnMouseDragged(e2 -> {
                gameOverStage.setX(e2.getScreenX() - e.getSceneX());
                gameOverStage.setY(e2.getScreenY() - e.getSceneY());
            });
        });

        gameOverStage.show();
    }

    /*private void showGameOverDialog(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("🎮 Fin de la partie");
        alert.setHeaderText(null);

        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        player1.calculateScore(game.getGrid());
        player2.calculateScore(game.getGrid());

        int scoreNoir = player1.getScore();
        int scoreBlanc = player2.getScore();

        String contentText;
        if (winner.equals(player1.getName())) {
            contentText = String.format("""
            🏆 Partie terminée !

            Score final :
            ⚫ Noir : %d
            ⚪ Blanc : %d

            🎊 Félicitations %s ! Vous avez gagné ! 🎊
            """, scoreNoir, scoreBlanc, winner);
        } else if (winner.equals(player2.getName())) {
            contentText = String.format("""
            🏆 Partie terminée !

            Score final :
            ⚫ Noir : %d
            ⚪ Blanc : %d

            🎊 Félicitations %s ! Vous avez gagné ! 🎊
            """, scoreNoir, scoreBlanc, winner);
        } else {
            contentText = String.format("""
            🤝 Match nul !

            Score final :
            ⚫ Noir : %d
            ⚪ Blanc : %d

            Belle partie !
            """, scoreNoir, scoreBlanc);
        }

        alert.setContentText(contentText);

        ButtonType newGameButton = new ButtonType("Nouvelle Partie");
        alert.getButtonTypes().setAll(newGameButton);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("""
        -fx-background-color: #f0f0f0;
        -fx-font-family: 'Segoe UI', sans-serif;
        -fx-font-size: 14px;
        """);

        alert.showAndWait().ifPresent(__ -> game.resetGame());
    }*/

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