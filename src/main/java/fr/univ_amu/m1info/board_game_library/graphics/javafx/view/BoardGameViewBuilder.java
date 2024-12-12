package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

import fr.univ_amu.m1info.board_game_library.graphics.javafx.bar.BarPosition;

public interface BoardGameViewBuilder {
    BoardGameViewBuilder resetView();
    BoardGameViewBuilder setBoardGameDimensions(int rowCount, int columnCount);
    BoardGameViewBuilder setTitle(String title);
    BoardGameViewBuilder addLabel(String id, String initialText, BarPosition position, int row, int column, int rowSpan, int columnSpan);
    BoardGameViewBuilder addButton(String id, String label, BarPosition barPosition, int row, int column, int rowSpan, int columnSpan);
    BoardGameControllableView getView();
}