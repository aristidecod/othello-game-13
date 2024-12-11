package fr.univ_amu.m1info.board_game_library.model;

import fr.univ_amu.m1info.board_game_library.model.command.Command;
import fr.univ_amu.m1info.board_game_library.model.command.MoveCommand;
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
    private final Deque<Command> undoHistory;
    private final Deque<Command> redoHistory;
    private boolean isRedoing = false;

    public Game() {
        this.grid = new Grid();
        this.player1 = new Player("BLACK", PlayerColor.BLACK);
        this.player2 = new Player("WHITE", PlayerColor.WHITE);
        this.currentPlayer = player1;
        Command command = new MoveCommand(this);
        this.undoHistory = new LinkedList<>();
        this.redoHistory = new LinkedList<>();
        undoHistory.push(command);
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
            undoHistory.push(command);
            // On efface l'historique de redo seulement si ce n'est pas un redo
            if (!isRedoing) {
                redoHistory.clear();
            }
            int MAX_UNDO = 61;
            if (undoHistory.size() > MAX_UNDO) {
                undoHistory.removeLast();
            }
            return true;
        }
        return false;
    }

    public boolean canUndo() {
        return undoHistory.size() > 1;
    }

    public boolean canRedo() {
        return !redoHistory.isEmpty();
    }

    public void undo() {
        Command command = undoHistory.pop();
        command.undo();
        redoHistory.push(command);
    }

    public void redo() {
        isRedoing = true;
        Command command = redoHistory.pop();
        if (command.execute(command.getLastPosition())) {
            undoHistory.push(command);
        }
        isRedoing = false;
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
        undoHistory.clear();
        redoHistory.clear();
        updateScores();
    }

    public void updateScores() {
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

    public boolean isGameOver() {
        List<BoardPosition> validMovesPlayer1 = getGrid().findValidMoves(getPlayer1().getColor());
        List<BoardPosition> validMovesPlayer2 = getGrid().findValidMoves(getPlayer2().getColor());
        return validMovesPlayer1.isEmpty() && validMovesPlayer2.isEmpty();
    }
}