package fr.univ_amu.m1info.board_game_library.model;

public enum PlayerColor {
    BLACK,
    WHITE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
