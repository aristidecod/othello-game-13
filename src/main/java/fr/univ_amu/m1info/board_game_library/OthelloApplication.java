package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.controller.OthelloController;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.JavaFXBoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.*;

import java.util.List;

public class OthelloApplication {
    public static void main(String[] args) {
        BoardGameConfiguration boardGameConfiguration = new BoardGameConfiguration(
                "Othello Game",
                new BoardGameDimensions(8, 8),
                List.of(
                        // Contrôles en haut
                        new LabeledElementConfiguration(
                                "New Game",
                                "NewGame",
                                LabeledElementKind.BUTTON,
                                new Position(0, 0, 1, 1)
                        ),
                        new LabeledElementConfiguration(
                                "Show Console Grid",
                                "ShowConsole",
                                LabeledElementKind.BUTTON,
                                new Position(0, 1, 1, 1)
                        ),

                        // Undo/Redo
                        new LabeledElementConfiguration(
                                "Undo",
                                "Undo",
                                LabeledElementKind.BUTTON,
                                new Position(0, 2, 1, 1)
                        ),
                        new LabeledElementConfiguration(
                                "Redo",
                                "Redo",
                                LabeledElementKind.BUTTON,
                                new Position(0, 3, 1, 1)
                        ),

                        // Informations joueur et jeu
                        new LabeledElementConfiguration(
                                "Current Player",
                                "currentPlayerLabel",
                                LabeledElementKind.TEXT,
                                new Position(1, 0, 2, 1)
                        ),
                        new LabeledElementConfiguration(
                                "",
                                "Info",
                                LabeledElementKind.TEXT,
                                new Position(1, 1, 2, 1)
                        ),

                        // Scores
                        new LabeledElementConfiguration(
                                "",
                                "player1Score",
                                LabeledElementKind.TEXT,
                                new Position(3, 0, 2, 1)
                        ),
                        new LabeledElementConfiguration(
                                "",
                                "player2Score",
                                LabeledElementKind.TEXT,
                                new Position(3, 1, 2, 1)
                        )
                )
        );

        BoardGameController controller = new OthelloController();
        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(boardGameConfiguration, controller);
    }
}