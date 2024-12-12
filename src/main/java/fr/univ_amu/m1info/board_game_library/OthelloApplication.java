package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.controller.OthelloController;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.JavaFXBoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.*;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.bar.BarPosition;

import java.util.List;

public class OthelloApplication {
    public static void main(String[] args) {
        BoardGameConfiguration boardGameConfiguration = new BoardGameConfiguration(
                "Othello Game",
                new BoardGameDimensions(8, 8),
                List.of(
                        // Barre supérieure - première ligne
                        new LabeledElementConfiguration(
                                "",
                                "Info",
                                LabeledElementKind.TEXT,
                                new Position(1, 6, 1, 3),
                                BarPosition.TOP
                        ),
                        // Barre supérieure - deuxième ligne
                        new LabeledElementConfiguration(
                                "Current Player:",
                                "currentPlayerLabel",
                                LabeledElementKind.TEXT,
                                new Position(0, 6, 1, 2),
                                BarPosition.TOP
                        ),
                        // Scores sur la même ligne avec espace entre eux
                        new LabeledElementConfiguration(
                                "",
                                "player1Score",
                                LabeledElementKind.TEXT,
                                new Position(1, 0, 1, 2),
                                BarPosition.TOP
                        ),
                        new LabeledElementConfiguration(
                                "",
                                "player2Score",
                                LabeledElementKind.TEXT,
                                new Position(1, 12, 1, 2),
                                BarPosition.TOP
                        ),

                        // Barre inférieure - Contrôles sur une ligne
                        new LabeledElementConfiguration(
                                "New Game",
                                "NewGame",
                                LabeledElementKind.BUTTON,
                                new Position(3, 0, 1, 1),
                                BarPosition.BOTTOM
                        ),
                        new LabeledElementConfiguration(
                                "Undo",
                                "Undo",
                                LabeledElementKind.BUTTON,
                                new Position(3, 12, 1, 1),
                                BarPosition.BOTTOM
                        ),
                        new LabeledElementConfiguration(
                                "Redo",
                                "Redo",
                                LabeledElementKind.BUTTON,
                                new Position(3, 13, 1, 1),
                                BarPosition.BOTTOM
                        ),
                        new LabeledElementConfiguration(
                                "AI Mode",
                                "AIToggle",
                                LabeledElementKind.BUTTON,
                                new Position(3, 14, 1, 1),
                                BarPosition.BOTTOM
                        ),
                        // Ligne vide en bas pour l'espacement
                        new LabeledElementConfiguration(
                                "",
                                "BottomSpacer",
                                LabeledElementKind.TEXT,
                                new Position(4, 0, 1, 15),
                                BarPosition.BOTTOM
                        )
                )
        );

        BoardGameController controller = new OthelloController();
        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(boardGameConfiguration, controller);
    }
}