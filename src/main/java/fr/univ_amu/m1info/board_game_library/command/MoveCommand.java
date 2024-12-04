package fr.univ_amu.m1info.board_game_library.command;

import fr.univ_amu.m1info.board_game_library.model.Game;
import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.BoardPosition;

public class MoveCommand implements Command {
    private final Game game;
    private final Grid previousGridState;
    private final Player previousPlayer;
    private BoardPosition lastPosition;

    public MoveCommand(Game game) {
        this.game = game;
        this.previousGridState = game.getGrid().clone();
        this.previousPlayer = game.getCurrentPlayer();
    }

    @Override
    public boolean execute(BoardPosition position) {
        this.lastPosition = position;
        return game.makeMove(position);
    }

    @Override
    public void undo() {
        game.setGrid(previousGridState.clone());
        game.setCurrentPlayer(previousPlayer);
    }

    @Override
    public BoardPosition getLastPosition() {
        return lastPosition;
    }
}