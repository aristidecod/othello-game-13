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
            this.player1 = new Player("Placida 1", Color.BLACK);
            this.player2 = new Player("Placida 2", Color.WHITE);
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
            highlightValidMoves();
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

        private void highlightValidMoves() {
            List<int[]> validMoves = grid.findValidMoves(currentPlayer.getColor());
            for (int[] move : validMoves) {
                int row = move[0];
                int col = move[1];
                // Utilise une couleur spécifique pour indiquer un coup possible
                view.setCellColor(row, col, fr.univ_amu.m1info.board_game_library.graphics.Color.LIGHTBLUE);
            }
        }

        private void updateCurrentPlayerIndicator() {
            String playerText = "Current Player: " + currentPlayer.getName();
            view.updateLabeledElement("currentPlayerLabel", playerText, true);
        }

        private void clearBoardHighlights() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    boolean isEven = (row + col) % 2 == 0;
                    view.setCellColor(row, col, isEven ? fr.univ_amu.m1info.board_game_library.graphics.Color.GREEN : fr.univ_amu.m1info.board_game_library.graphics.Color.LIGHTGREEN);
                }
            }
        }

        @Override
        public void boardActionOnClick(int row, int column) {
            if (currentPlayer.play(row, column, grid)) {
                String message = "";
                view.updateLabeledElement("Info", message, true);

                displayCurrentBoard();

                // Changer de joueur
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
                updateCurrentPlayerIndicator();

                // Nettoie et met en évidence les nouveaux coups possibles pour le joueur actuel
                clearBoardHighlights();
                highlightValidMoves();
            }
            else {
                String message = "Invalid Move";
                view.updateLabeledElement("Info", message, true);
            }
        }


        @Override
        public void buttonActionOnClick(String buttonId) {
            switch (buttonId) {
                case "NewGame" -> {
                    grid = new Grid();
                    for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                            view.removeShapesAtCell(row, col);
                        }
                    }
                    currentPlayer = player1;
                    displayCurrentBoard();
                    updateCurrentPlayerIndicator();

                    // Nettoie les indications et met en évidence les coups possibles pour le premier joueur
                    clearBoardHighlights();
                    highlightValidMoves();
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
                        new LabeledElementConfiguration("Current Player", "currentPlayerLabel", LabeledElementKind.TEXT),
                        new LabeledElementConfiguration("", "Info", LabeledElementKind.TEXT)
                )
        );

        BoardGameController controller = new OthelloController();
        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(boardGameConfiguration, controller);
    }


}