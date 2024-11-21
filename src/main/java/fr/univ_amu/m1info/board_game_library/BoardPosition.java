package fr.univ_amu.m1info.board_game_library;

public record BoardPosition(int row, int col) {

    @Override
    public String toString() {
        return String.format("Position(%d, %d)", row, col);
    }
}
