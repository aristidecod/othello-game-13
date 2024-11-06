package fr.univ_amu.m1info.board_game_library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @Test
    public void testPlayValidMove() {
        Player player;
        Grid grid;

        player = new Player("Player 1", Color.BLACK);
        grid = new Grid();

        boolean result = player.play(5, 7, grid);
        // Vérifie que le coup a été joué et que le pion a été placé
        assertTrue(result, "Le coup doit être valide");
        assertEquals(Color.BLACK, grid.getPion(5, 7).getColor(), "Le pion doit être placé en (5, 7) avec la couleur noire");
    }

    @Test
    public void testPlayInvalidMove() {
        Player player;
        Grid grid;

        player = new Player("Player 1", Color.BLACK);
        grid = new Grid();

        boolean result = player.play(9, 9, grid);
        // Vérifie que le coup n'a pas été joué et qu'aucun pion n'a été placé
        assertFalse(result, "Le coup doit être invalide");
    }

    @Test
    public void testCalculateScore() {
        Player player;
        Grid grid;

        player = new Player("Player 1", Color.BLACK);
        grid = new Grid();

        // Le joueur joue plusieurs coups
        player.play(0, 0, grid);
        player.play(1, 0, grid);
        player.play(2, 0, grid);

        // Un autre joueur ou des pions de couleur différente sont ajoutés manuellement
        grid.placePion(0, 1, new Pion(Color.WHITE));
        grid.placePion(1, 1, new Pion(Color.WHITE));
        grid.placePion(2, 1, new Pion(Color.WHITE));

        player.calculateScore(grid);
        assertEquals(5, player.getScore(), "Le score doit être calculé en fonction du nombre de pions de la couleur du joueur sachant qu'il y'a deja deux pions placer");
    }
}
