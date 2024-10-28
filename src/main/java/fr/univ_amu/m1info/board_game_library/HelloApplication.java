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
        private Player player1;
        private Player player2;
        private Player currentPlayer;

        public OthelloController() {
            this.grid = new Grid();
            this.player1 = new Player("Player 1", Color.BLACK);
            this.player2 = new Player("Player 2", Color.WHITE);
            this.currentPlayer = player1;
        }

        @Override
        public void initializeViewOnStart(BoardGameView view) {
            this.view = view;
            // Initialise le plateau avec une couleur d'échiquier
            initializeBoard();
            // Affiche les pions initiaux
            displayCurrentBoard();
            // Met à jour l'élément de l'interface pour afficher le joueur actuel
            updateCurrentPlayerIndicator();
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

        private void updateCurrentPlayerIndicator() {
            String playerText = "Current Player: " + currentPlayer.getName();
            view.updateLabeledElement("currentPlayerLabel", playerText, true);
        }

        @Override
        public void boardActionOnClick(int row, int column) {
            if (currentPlayer.play(row, column, grid)) {
                displayCurrentBoard();
                // Changer de joueur
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
                updateCurrentPlayerIndicator();
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
                    currentPlayer = player1;
                    displayCurrentBoard();
                    updateCurrentPlayerIndicator();
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
                        new LabeledElementConfiguration("Show Console Grid", "ShowConsole", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Current Player", "currentPlayerLabel", LabeledElementKind.TEXT)
                )
        );

        BoardGameController controller = new OthelloController();
        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(boardGameConfiguration, controller);
    }
}