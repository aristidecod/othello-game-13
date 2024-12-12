package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

import fr.univ_amu.m1info.board_game_library.graphics.javafx.bar.Bar;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.bar.BarPosition;
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
    public BoardGameViewBuilder addButton(String id, String label, BarPosition barPosition, int row, int column, int rowSpan, int columnSpan) {
        Bar targetBar = switch (barPosition) {
            case TOP -> boardGameView.getTopBar();
            case BOTTOM -> boardGameView.getBottomBar();
        };

        targetBar.addButton(id, label, row, column, rowSpan, columnSpan);
        targetBar.setButtonAction(id, () -> boardGameView.buttonActionOnclick(id));
        return this;
    }

    @Override
    public BoardGameViewBuilder addLabel(String id, String initialText, BarPosition position, int row, int column, int rowSpan, int columnSpan) {
        switch (position) {
            case TOP:
                boardGameView.getTopBar().addLabel(id, initialText, row, column, rowSpan, columnSpan);
                break;
            case BOTTOM:
                boardGameView.getBottomBar().addLabel(id, initialText, row, column, rowSpan, columnSpan);
                break;
        }
        return this;
    }

    @Override
    public BoardGameControllableView getView() {
        return boardGameView;
    }
}
