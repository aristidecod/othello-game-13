package fr.univ_amu.m1info.board_game_library;

public interface Command {
    boolean execute(int row, int column);
    void undo();
}