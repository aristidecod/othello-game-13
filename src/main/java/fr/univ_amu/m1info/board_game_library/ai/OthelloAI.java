package fr.univ_amu.m1info.board_game_library.ai;

import fr.univ_amu.m1info.board_game_library.model.BoardPosition;
import fr.univ_amu.m1info.board_game_library.model.Game;
import fr.univ_amu.m1info.board_game_library.model.PlayerColor;

public interface OthelloAI {
    BoardPosition findBestMove(Game game, PlayerColor aiColor);
}