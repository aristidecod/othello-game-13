package fr.univ_amu.m1info.board_game_library;

public class MoveCommand implements Command {
    private final GameLogic gameLogic;
    private final Grid previousGridState;
    private final Player previousPlayer;

    public MoveCommand(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.previousGridState = gameLogic.getGrid().clone();
        this.previousPlayer = gameLogic.getCurrentPlayer();
    }

    @Override
    public boolean execute(int row, int column) {
        return gameLogic.makeMove(row, column);
    }

    @Override
    public void undo() {
        gameLogic.setGrid(previousGridState.clone());
        gameLogic.setCurrentPlayer(previousPlayer);
    }
}