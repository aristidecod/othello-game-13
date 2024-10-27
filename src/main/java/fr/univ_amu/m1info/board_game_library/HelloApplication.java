package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementKind;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameDimensions;

import java.util.List;

public class HelloApplication {

    private static class OthelloController implements BoardGameController {
        private BoardGameView view;
        private Grid grid;

        public OthelloController() {
            this.grid = new Grid();
        }

        @Override
        public void initializeViewOnStart(BoardGameView view) {
            this.view = view;
            // Initialise le plateau avec une couleur d'échiquier
            initializeBoard();
            // Affiche les pions initiaux
            displayCurrentBoard();
        }

        private void initializeBoard() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    boolean isEven = (row + col) % 2 == 0;
                    view.setCellColor(row, col, isEven ? fr.univ_amu.m1info.board_game_library.graphics.Color.GREEN : fr.univ_amu.m1info.board_game_library.graphics.Color.LIGHTGREEN);
                }
            }
        }

        private void displayCurrentBoard() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Pion pion = grid.getPion(row, col);
                    if (pion != null) {
                        fr.univ_amu.m1info.board_game_library.graphics.Color pieceColor =
                                pion.getColor() == Color.BLACK ?
                                        fr.univ_amu.m1info.board_game_library.graphics.Color.BLACK :
                                        fr.univ_amu.m1info.board_game_library.graphics.Color.WHITE;
                        view.addShapeAtCell(row, col, Shape.CIRCLE, pieceColor);
                    }
                }
            }
        }

        @Override
        public void boardActionOnClick(int row, int column) {
            if (grid.isValidMove(row, column)) {
                grid.placePion(row, column, new Pion(Color.BLACK));
                displayCurrentBoard();
            }
        }

        @Override
        public void buttonActionOnClick(String buttonId) {
            switch (buttonId) {
                case "NewGame" -> {
                    grid = new Grid();
                    // Nettoie le plateau
                    for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                            view.removeShapesAtCell(row, col);
                        }
                    }
                    displayCurrentBoard();
                }
                case "ShowConsole" -> {
                    grid.displayGrid();
                }
            }
        }
    }

    public static void main(String[] args) {
        BoardGameConfiguration boardGameConfiguration = new BoardGameConfiguration(
                "Othello Game",
                new BoardGameDimensions(8, 8),
                List.of(
                        new LabeledElementConfiguration("New Game", "NewGame", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Show Console Grid", "ShowConsole", LabeledElementKind.BUTTON)
                )
        );

        BoardGameController controller = new OthelloController();
        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(boardGameConfiguration, controller);
    }
}