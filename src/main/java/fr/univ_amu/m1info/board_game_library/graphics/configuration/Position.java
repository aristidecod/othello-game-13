package fr.univ_amu.m1info.board_game_library.graphics.configuration;

public record Position(int row, int column, int rowSpan, int columnSpan) {
    public Position {
        if (row < 0 || column < 0 || rowSpan <= 0 || columnSpan <= 0) {
            throw new IllegalArgumentException("Invalid position parameters");
        }
    }
}
