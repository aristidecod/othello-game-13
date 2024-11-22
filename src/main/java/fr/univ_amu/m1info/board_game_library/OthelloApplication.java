package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.JavaFXBoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameDimensions;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementKind;

import java.util.List;

public class OthelloApplication {
    public static void main(String[] args) {
        BoardGameConfiguration boardGameConfiguration = new BoardGameConfiguration(
                "Othello Game",
                new BoardGameDimensions(8, 8),
                List.of(
                        new LabeledElementConfiguration("New Game", "NewGame", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Show Console Grid", "ShowConsole", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Undo", "Undo", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Current Player", "currentPlayerLabel", LabeledElementKind.TEXT),
                        new LabeledElementConfiguration("", "Info", LabeledElementKind.TEXT),
                        new LabeledElementConfiguration("", "player1Score", LabeledElementKind.TEXT),
                        new LabeledElementConfiguration("", "player2Score", LabeledElementKind.TEXT)
                )
        );

        BoardGameController controller = new OthelloController();
        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(boardGameConfiguration, controller);
    }
}