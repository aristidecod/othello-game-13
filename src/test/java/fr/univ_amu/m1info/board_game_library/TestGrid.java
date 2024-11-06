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
}