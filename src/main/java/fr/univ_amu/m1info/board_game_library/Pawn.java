package fr.univ_amu.m1info.board_game_library;

public class Pawn {
    private PlayerColor playerColor;

    public Pawn(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public PlayerColor getColor() {
        return playerColor;
    }

    public void setColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }
}