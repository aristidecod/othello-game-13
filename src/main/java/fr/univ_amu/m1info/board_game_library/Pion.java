package fr.univ_amu.m1info.board_game_library;

public class Pion {
    private Color color;

    public Pion(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}