package fr.univ_amu.m1info.board_game_library;

public enum Color {
    BLACK,
    WHITE;

    public Color getOppositeColor() {
        return this == BLACK ? WHITE : BLACK;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
