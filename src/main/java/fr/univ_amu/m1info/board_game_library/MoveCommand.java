package fr.univ_amu.m1info.board_game_library;

public class MoveCommand implements Command {
    private final GameLogic gameLogic;
    private final int row;
    private final int column;
    private final Grid previousGridState;
    private final Player previousPlayer;

    public MoveCommand(GameLogic gameLogic, int row, int column) {
        this.gameLogic = gameLogic;
        this.row = row;
        this.column = column;
        this.previousGridState = gameLogic.getGrid().clone(); // Il faudra implémenter clone()
        this.previousPlayer = gameLogic.getCurrentPlayer();
    }

    @Override
    public void execute() {
        gameLogic.makeMove(row, column);
        gameLogic.switchPlayer();
    }

    @Override
    public void undo() {
        gameLogic.setGrid(previousGridState.clone());
        gameLogic.setCurrentPlayer(previousPlayer);
    }
}