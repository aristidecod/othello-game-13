package fr.univ_amu.m1info.board_game_library;

public enum PlayerColor {
    BLACK,
    WHITE;

    public PlayerColor getOppositeColor() {
        return this == BLACK ? WHITE : BLACK;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
