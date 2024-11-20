package fr.univ_amu.m1info.board_game_library;

public class Pawn {
    private Color color;

    public Pawn(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}