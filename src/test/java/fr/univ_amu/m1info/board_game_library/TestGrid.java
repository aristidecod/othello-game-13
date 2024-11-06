package fr.univ_amu.m1info.board_game_library;

import org.junit.jupiter.api.Test;

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
}