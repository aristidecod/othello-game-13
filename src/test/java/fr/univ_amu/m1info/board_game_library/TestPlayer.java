package fr.univ_amu.m1info.board_game_library;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @Test
    public void testPlayValidMove() {
        Player player;
        Grid grid;

        player = new Player("Player 1", PlayerColor.BLACK);
        grid = new Grid();

        boolean result = player.play(3, 2, grid);
        // Vérifie que le coup a été joué et que le pion a été placé
        assertTrue(result, "Le coup doit être valide");
        assertEquals(PlayerColor.BLACK, grid.getPawn(3, 2).getColor(), "Le pion doit être placé en (3, 2) avec la couleur noire");
    }

    @Test
    public void testPlayInvalidMove() {
        Player player;
        Grid grid;

        player = new Player("Player 1", PlayerColor.BLACK);
        grid = new Grid();

        boolean result = player.play(6, 7, grid);
        // Vérifie que le coup n'a pas été joué et qu'aucun pion n'a été placé
        assertFalse(result, "Le coup doit être invalide");
    }

    @Test
    public void testCalculateScore() {
        Player player;
        Grid grid;

        player = new Player("Player 1", PlayerColor.BLACK);
        grid = new Grid();

        // Le joueur joue plusieurs coups
        player.play(3, 2, grid);
        player.play(2, 3, grid);
        player.play(4, 5, grid);

        player.calculateScore(grid);
        assertEquals(5, player.getScore(), "Le score doit être calculé en fonction du nombre de pions de la couleur du joueur sachant qu'il y'a deja deux pions placer");
    }
}
