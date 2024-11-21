package fr.univ_amu.m1info.board_game_library;

public interface BoardIterator {
    boolean hasNext();
    BoardPosition next();
    void reset();
}
