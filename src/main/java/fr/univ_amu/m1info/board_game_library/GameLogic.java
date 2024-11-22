package fr.univ_amu.m1info.board_game_library;

import java.util.List;

public class GameLogic {
    private Grid grid;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private OthelloView view; // Nouvel attribut

    public GameLogic() {
        this.grid = new Grid();
        this.player1 = new Player("Placida", PlayerColor.BLACK);
        this.player2 = new Player("Jonas", PlayerColor.WHITE);
        this.currentPlayer = player1;
        updateScores();
    }

    // Nouvelle méthode pour définir la vue
    public void setView(OthelloView view) {
        this.view = view;
        updateScores(); // Met à jour les scores initiaux
    }

    public Grid getGrid() {
        return grid;
    }

    public List<int[]> getValidMoves() {
        return grid.findValidMoves(currentPlayer.getColor());
    }

    public boolean makeMove(int row, int column) {
        boolean moveSuccess = currentPlayer.play(row, column, grid);
        if (moveSuccess) {
            updateScores();
        }
        return moveSuccess;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public String getCurrentPlayerName() {
        return currentPlayer.getName();
    }

    public void resetGame() {
        grid = new Grid();
        currentPlayer = player1;
        updateScores();
    }

    private void updateScores() {
        player1.calculateScore(grid);
        player2.calculateScore(grid);
        if (view != null) {
            view.updateScores(player1, player2);
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getPlayer1Score() {
        return player1.getScore();
    }

    public int getPlayer2Score() {
        return player2.getScore();
    }
}