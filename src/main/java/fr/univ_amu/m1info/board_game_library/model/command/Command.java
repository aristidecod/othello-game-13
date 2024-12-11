package fr.univ_amu.m1info.board_game_library.model.command;

import fr.univ_amu.m1info.board_game_library.model.BoardPosition;

public interface Command {
    boolean execute(BoardPosition position);
    void undo();
    BoardPosition getLastPosition();
}