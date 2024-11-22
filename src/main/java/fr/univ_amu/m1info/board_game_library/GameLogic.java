package fr.univ_amu.m1info.board_game_library;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameLogic {
    private Grid grid;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final Deque<Command> commandHistory;
    private static final int MAX_UNDO = 61;

    public GameLogic() {
        this.grid = new Grid();
        this.player1 = new Player("PLAYER 1", PlayerColor.BLACK);
        this.player2 = new Player("PLAYER 2", PlayerColor.WHITE);
        this.currentPlayer = player1;
        Command command = new MoveCommand(this, 0, 0);
        this.commandHistory = new LinkedList<>();
        commandHistory.push(command);
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean executeMove(int row, int column) {
        if (makeMove(row, column)) {
            MoveCommand command = new MoveCommand(this, row, column);
            commandHistory.push(command);
            System.out.println("tzillr : " + commandHistory.size());
            if (commandHistory.size() > MAX_UNDO) {
                commandHistory.removeLast();
            }
            System.out.println("bnbbb : " + commandHistory.size());
            return true;
        }
        return false;
    }

    public boolean canUndo() {
        return commandHistory.size() > 1;
    }

    public void undo() {
        Command command = commandHistory.pop();
        command = commandHistory.pop();
        commandHistory.push(command);
        System.out.println("aaaaa : " + commandHistory.size());
        command.undo();
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