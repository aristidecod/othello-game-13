package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

import javafx.stage.Stage;

public class JavaFXBoardGameViewBuilder implements BoardGameViewBuilder {
    JavaFXBoardGameView boardGameView;

    public JavaFXBoardGameViewBuilder(Stage primaryStage) {
        boardGameView = new JavaFXBoardGameView(primaryStage);
    }

    public BoardGameViewBuilder resetView(){
        boardGameView.reset();
        boardGameView.getBoardGridView().setAction(boardGameView::boardActionOnclick);
        return this;
    }


    @Override
    public BoardGameViewBuilder setBoardGameDimensions(int rowCount, int columnCount) {
        boardGameView.getBoardGridView().setDimensions(rowCount, columnCount);
        return this;
    }

    @Override
    public BoardGameViewBuilder setTitle(String title) {
        boardGameView.getStage().setTitle(title);
        return this;
    }

    @Override
    public BoardGameViewBuilder addLabel(String id, String initialText, int row, int column, int rowSpan, int columnSpan) {
        boardGameView.getBar().addLabel(id, initialText, row, column, rowSpan, columnSpan);
        return this;
    }

    @Override
    public BoardGameViewBuilder addButton(String id, String label, int row, int column, int rowSpan, int columnSpan) {
        boardGameView.getBar().addButton(id, label, row, column, rowSpan, columnSpan);
        boardGameView.getBar().setButtonAction(id, ()->boardGameView.buttonActionOnclick(id));
        return this;
    }

    @Override
    public BoardGameControllableView getView() {
        return boardGameView;
    }

}
