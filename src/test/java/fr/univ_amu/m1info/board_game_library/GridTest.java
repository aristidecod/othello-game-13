package fr.univ_amu.m1info.board_game_library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

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
