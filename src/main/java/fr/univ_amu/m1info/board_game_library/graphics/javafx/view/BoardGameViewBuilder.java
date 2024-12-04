package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

public interface BoardGameViewBuilder {
    BoardGameViewBuilder resetView();
    BoardGameViewBuilder setBoardGameDimensions(int rowCount, int columnCount);
    BoardGameViewBuilder setTitle(String title);
    BoardGameViewBuilder addLabel(String id, String initialText, int row, int column, int rowSpan, int columnSpan);
    BoardGameViewBuilder addButton(String id, String label, int row, int column, int rowSpan, int columnSpan);
    BoardGameControllableView getView();
}