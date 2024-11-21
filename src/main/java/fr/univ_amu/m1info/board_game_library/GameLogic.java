package fr.univ_amu.m1info.board_game_library;

import java.util.List;

public class GameLogic {
    private Grid grid;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    public GameLogic() {
        this.grid = new Grid();
        this.player1 = new Player("PLAYER 1", PlayerColor.BLACK);
        this.player2 = new Player("PLAYER 2", PlayerColor.WHITE);
        this.currentPlayer = player1;
    }

    public Grid getGrid() {
        return grid;
    }

    public List<int[]> getValidMoves() {
        return grid.findValidMoves(currentPlayer.getColor());
    }

    public boolean makeMove(int row, int column) {
        return currentPlayer.play(row, column, grid);
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
    }
}