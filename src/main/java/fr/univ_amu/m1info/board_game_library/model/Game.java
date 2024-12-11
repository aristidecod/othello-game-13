package fr.univ_amu.m1info.board_game_library.model;

import fr.univ_amu.m1info.board_game_library.command.Command;
import fr.univ_amu.m1info.board_game_library.command.MoveCommand;
import fr.univ_amu.m1info.board_game_library.view.OthelloView;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Game implements Cloneable {
    private Grid grid;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private OthelloView view;
    private final Deque<Command> commandHistory;

    public Game() {
        this.grid = new Grid();
        this.player1 = new Player("Moi", PlayerColor.BLACK);
        this.player2 = new Player("Toi", PlayerColor.WHITE);
        this.currentPlayer = player1;
        Command command = new MoveCommand(this);
        this.commandHistory = new LinkedList<>();
        commandHistory.push(command);
    }

    @Override
    public Game clone() {
        try {
            Game clonedGame = (Game) super.clone();
            clonedGame.grid = this.grid.clone();
            return clonedGame;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Should not happen");
        }
    }

    public void setView(OthelloView view) {
        this.view = view;
        updateScores();
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

    public boolean executeMove(BoardPosition position) {
        MoveCommand command = new MoveCommand(this);
        if (command.execute(position)) {
            commandHistory.push(command);
            int MAX_UNDO = 61;
            if (commandHistory.size() > MAX_UNDO) {
                commandHistory.removeLast();
            }
            return true;
        }
        return false;
    }

    public boolean canUndo() {
        return commandHistory.size() > 1;
    }

    public void undo() {
        Command command = commandHistory.pop();
        command.undo();
    }

    public Grid getGrid() {
        return grid;
    }

    public List<BoardPosition> getValidMoves() {
        return grid.findValidMoves(currentPlayer.getColor());
    }

    public boolean makeMove(BoardPosition position) {
        boolean moveSuccess = currentPlayer.play(position, grid);
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

    public boolean isOver() {
        return getValidMoves().isEmpty();
    }

    public void checkGameOver() {
        // Vérifie s'il y a des coups valides pour l'un ou l'autre des joueurs
        List<BoardPosition> validMovesPlayer1 = getGrid().findValidMoves(getPlayer1().getColor());
        List<BoardPosition> validMovesPlayer2 = getGrid().findValidMoves(getPlayer2().getColor());

        if (validMovesPlayer1.isEmpty() && validMovesPlayer2.isEmpty()) {
            // Compte les pions de chaque joueur
            int player1Score = getPlayer1().getScore();
            int player2Score = getPlayer2().getScore();

            String winner;
            if (player1Score > player2Score) {
                winner = getPlayer1().getName();
            } else if (player2Score > player1Score) {
                winner = getPlayer2().getName();
            } else {
                winner = "Égalité";
            }

            javafx.application.Platform.runLater(() -> {
                view.showGameOverDialog(winner,player1Score,player2Score);
            });
        }
    }
}