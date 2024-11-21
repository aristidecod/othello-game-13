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

        // Vérifie que les Pawns sont placés correctement
        assertNotNull(grid.getPawn(3, 3));
        assertEquals(PlayerColor.WHITE, grid.getPawn(3, 3).getColor());

        assertNotNull(grid.getPawn(4, 4));
        assertEquals(PlayerColor.WHITE, grid.getPawn(4, 4).getColor());

        assertNotNull(grid.getPawn(3, 4));
        assertEquals(PlayerColor.BLACK, grid.getPawn(3, 4).getColor());

        assertNotNull(grid.getPawn(4, 3));
        assertEquals(PlayerColor.BLACK, grid.getPawn(4, 3).getColor());

        // Vérifie que les autres cases sont vides
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!((i == 3 || i == 4) && (j == 3 || j == 4))) {
                    assertNull(grid.getPawn(i, j));
                }
            }
        }
    }

    @Test
    public void placePawn_validMove() {
        Grid grid = new Grid();
        Pawn pawn = new Pawn(PlayerColor.BLACK);

        //suppoons que le pawn noir est placé sur la case 5,4
        boolean isPawnPlaced = grid.placePawn(2, 3, pawn);

        assertTrue(isPawnPlaced, "The pawn should be placed on the grid as the move is valid.");
        assertEquals(pawn, grid.getPawn(2, 3), "The placed pawn should be retrieved from the grid.");
    }

    @Test
    public void placePawn_invalidMove() {
        Grid grid = new Grid();
        Pawn pawn = new Pawn(PlayerColor.BLACK);

        boolean isPawnPlaced = grid.placePawn(0, 0, pawn);

        assertFalse(isPawnPlaced, "The pawn should not be placed on the grid as the move is invalid.");
        assertEquals(null, grid.getPawn(0, 0), "The grid at the specified point should be null as pawn was not placed.");
    }

    @Test
    public void testIsValidMove() {
        Grid grid = new Grid();
        // Teste les coups valides autour des positions centrales pour les deux couleurs
        assertTrue(grid.isValidMove(2, 3, PlayerColor.BLACK), "Le coup (2, 3) devrait être valide pour BLACK");
        assertTrue(grid.isValidMove(3, 2, PlayerColor.BLACK), "Le coup (3, 2) devrait être valide pour BLACK");
        assertTrue(grid.isValidMove(4, 5, PlayerColor.BLACK), "Le coup (4, 5) devrait être valide pour BLACK");
        assertTrue(grid.isValidMove(5, 4, PlayerColor.BLACK), "Le coup (5, 4) devrait être valide pour BLACK");

        // Teste quelques coups invalides
        assertFalse(grid.isValidMove(3, 3, PlayerColor.BLACK), "Le coup (3, 3) ne devrait pas être valide car il est occupé");
        assertFalse(grid.isValidMove(0, 0, PlayerColor.BLACK), "Le coup (0, 0) ne devrait pas être valide car il n'y a pas de Pawns à retourner");
    }

    @Test
    public void testFindValidMoves() {
        Grid grid = new Grid();
        // Vérifie les coups valides pour la configuration initiale
        List<int[]> validMoves = grid.findValidMoves(PlayerColor.BLACK);

        // On s'attend à avoir 4 coups valides au départ pour le joueur noir
        assertEquals(4, validMoves.size(), "Il devrait y avoir 4 coups valides pour BLACK au départ");

        // Vérifie que les coups spécifiques sont présents
        assertTrue(validMoves.stream().anyMatch(move -> move[0] == 2 && move[1] == 3), "Le coup (2, 3) devrait être valide");
        assertTrue(validMoves.stream().anyMatch(move -> move[0] == 3 && move[1] == 2), "Le coup (3, 2) devrait être valide");
        assertTrue(validMoves.stream().anyMatch(move -> move[0] == 4 && move[1] == 5), "Le coup (4, 5) devrait être valide");
        assertTrue(validMoves.stream().anyMatch(move -> move[0] == 5 && move[1] == 4), "Le coup (5, 4) devrait être valide");
    }
}