package fr.univ_amu.m1info.board_game_library;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Cette class teste les méthodes de la class Grid.
 */

public class TestGrid {

    @Test
    public void testInitializeGrid() {
        Grid grid = new Grid();
        grid.initializeGrid();

        // Vérifie que les pions sont placés correctement
        assertNotNull(grid.getPion(3, 3));
        assertEquals(Color.WHITE, grid.getPion(3, 3).getColor());

        assertNotNull(grid.getPion(4, 4));
        assertEquals(Color.WHITE, grid.getPion(4, 4).getColor());

        assertNotNull(grid.getPion(3, 4));
        assertEquals(Color.BLACK, grid.getPion(3, 4).getColor());

        assertNotNull(grid.getPion(4, 3));
        assertEquals(Color.BLACK, grid.getPion(4, 3).getColor());

        // Vérifie que les autres cases sont vides
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!((i == 3 || i == 4) && (j == 3 || j == 4))) {
                    assertNull(grid.getPion(i, j));
                }
            }
        }
    }

    @Test
    public void placePion_validMove() {
        Grid grid = new Grid();
        Pion pion = new Pion(Color.BLACK);

        //suppoons que le pion noir est placé sur la case 5,4
        boolean isPionPlaced = grid.placePion(2, 3, pion);

        assertTrue(isPionPlaced, "The pion should be placed on the grid as the move is valid.");
        assertEquals(pion, grid.getPion(2, 3), "The placed pion should be retrieved from the grid.");
    }

    @Test
    public void placePion_invalidMove() {
        Grid grid = new Grid();
        Pion pion = new Pion(Color.BLACK);

        boolean isPionPlaced = grid.placePion(0, 0, pion);

        assertFalse(isPionPlaced, "The pion should not be placed on the grid as the move is invalid.");
        assertEquals(null, grid.getPion(0, 0), "The grid at the specified point should be null as pion was not placed.");
    }

    @Test
    public void testIsValidMove() {
        Grid grid = new Grid();
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
    public void testFindValidMoves() {
        Grid grid = new Grid();
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