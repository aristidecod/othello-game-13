package fr.univ_amu.m1info.board_game_library;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GridTest {
    private Grid grid;

    @BeforeEach
    public void setup() {
        grid = new Grid();
    }

    @Test
    public void testIsValidMoveForOthello_InitialSetup() {
        // Teste les coups valides autour des positions centrales pour les deux couleurs
        assertTrue(grid.isValidMove(2, 3, Color.BLACK), "Le coup (2, 3) devrait être valide pour BLACK");
        assertTrue(grid.isValidMove(3, 2, Color.BLACK), "Le coup (3, 2) devrait être valide pour BLACK");
        assertTrue(grid.isValidMove(4, 5, Color.BLACK), "Le coup (4, 5) devrait être valide pour BLACK");
        assertTrue(grid.isValidMove(5, 4, Color.BLACK), "Le coup (5, 4) devrait être valide pour BLACK");

        // Teste quelques coups invalides
        assertFalse(grid.isValidMove(3, 3, Color.BLACK), "Le coup (3, 3) ne devrait pas être valide car il est occupé");
        assertFalse(grid.isValidMove(0, 0, Color.BLACK), "Le coup (0, 0) ne devrait pas être valide car il n'y a pas de pions à retourner");
    }

    @Test
    public void testFindValidMoves_InitialSetup() {
        // Vérifie les coups valides pour la configuration initiale
        List<int[]> validMoves = grid.findValidMoves(Color.BLACK);

        // On s'attend à avoir 4 coups valides au départ pour le joueur noir
        assertEquals(4, validMoves.size(), "Il devrait y avoir 4 coups valides pour BLACK au départ");

        // Vérifie que les coups spécifiques sont présents
        assertTrue(validMoves.stream().anyMatch(move -> move[0] == 2 && move[1] == 3), "Le coup (2, 3) devrait être valide");
        assertTrue(validMoves.stream().anyMatch(move -> move[0] == 3 && move[1] == 2), "Le coup (3, 2) devrait être valide");
        assertTrue(validMoves.stream().anyMatch(move -> move[0] == 4 && move[1] == 5), "Le coup (4, 5) devrait être valide");
        assertTrue(validMoves.stream().anyMatch(move -> move[0] == 5 && move[1] == 4), "Le coup (5, 4) devrait être valide");
    }
}
