package fr.univ_amu.m1info.board_game_library.iterator;

import fr.univ_amu.m1info.board_game_library.model.BoardPosition;

public interface BoardIterator {
    boolean hasNext();
    BoardPosition next();
}
