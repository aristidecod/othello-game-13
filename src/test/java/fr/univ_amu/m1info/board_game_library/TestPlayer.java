package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.model.BoardPosition;
import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.PlayerColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @Test
    public void testPlayValidMove() {
        Player player;
        Grid grid;

        player = new Player("Player 1", PlayerColor.BLACK);
        grid = new Grid();

        BoardPosition position = new BoardPosition(3, 2);
        boolean result = player.play(position, grid);
        // Vérifie que le coup a été joué et que le pion a été placé
        assertTrue(result, "Le coup doit être valide");
        assertEquals(PlayerColor.BLACK, grid.getPawn(position).getColor(),
                "Le pion doit être placé en (3, 2) avec la couleur noire");
    }

    @Test
    public void testPlayInvalidMove() {
        Player player;
        Grid grid;

        player = new Player("Player 1", PlayerColor.BLACK);
        grid = new Grid();

        BoardPosition position = new BoardPosition(6, 7);
        boolean result = player.play(position, grid);
        // Vérifie que le coup n'a pas été joué et qu'aucun pion n'a été placé
        assertFalse(result, "Le coup doit être invalide");
    }

    @Test
    public void testCalculateScore() {
        Player player;
        Grid grid;

        player = new Player("Player 1", PlayerColor.BLACK);
        grid = new Grid();

        // Le joueur un seul coup
        BoardPosition position = new BoardPosition(3, 2);
        player.play(position, grid);

        player.calculateScore(grid);
        assertEquals(4, player.getScore(),
                "Le score doit être calculé en fonction du nombre de pions de la couleur du joueur sachant qu'il y'a deja deux pions placer");
    }
}
