package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.model.BoardPosition;
import fr.univ_amu.m1info.board_game_library.model.Direction;
import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.PlayerColor;
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
        assertNotNull(grid.getPawn(new BoardPosition(3, 3)));
        assertEquals(PlayerColor.WHITE, grid.getPawn(new BoardPosition(3, 3)).getColor());

        assertNotNull(grid.getPawn(new BoardPosition(4, 4)));
        assertEquals(PlayerColor.WHITE, grid.getPawn(new BoardPosition(4, 4)).getColor());

        assertNotNull(grid.getPawn(new BoardPosition(3, 4)));
        assertEquals(PlayerColor.BLACK, grid.getPawn(new BoardPosition(3, 4)).getColor());

        assertNotNull(grid.getPawn(new BoardPosition(4, 3)));
        assertEquals(PlayerColor.BLACK, grid.getPawn(new BoardPosition(4, 3)).getColor());

        // Vérifie que les autres cases sont vides
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!((i == 3 || i == 4) && (j == 3 || j == 4))) {
                    assertNull(grid.getPawn(new BoardPosition(i, j)));
                }
            }
        }
    }

    @Test
    public void testPlacePawn() {
        Grid grid = new Grid();
        Pawn pawn = new Pawn(PlayerColor.BLACK);

        boolean isPawnPlaced = grid.placePawn(new BoardPosition(2, 3), pawn);
        assertTrue(isPawnPlaced, "Le pion devrait être placé car le mouvement est valide.");
        assertEquals(pawn, grid.getPawn(new BoardPosition(2, 3)), "Le pion placé devrait être récupéré de la grille.");

        // Teste un mouvement invalide
        isPawnPlaced = grid.placePawn(new BoardPosition(0, 0), pawn);
        assertFalse(isPawnPlaced, "Le pion ne devrait pas être placé car le mouvement est invalide.");
        assertNull(grid.getPawn(new BoardPosition(0, 0)), "La grille à la position spécifiée devrait être null car le pion n'a pas été placé.");
    }

    @Test
    public void testIsValidMove() {
        Grid grid = new Grid();

        assertTrue(grid.isValidMove(new BoardPosition(2, 3), PlayerColor.BLACK), "Le coup (2, 3) devrait être valide pour BLACK");
        assertTrue(grid.isValidMove(new BoardPosition(3, 2), PlayerColor.BLACK), "Le coup (3, 2) devrait être valide pour BLACK");
        assertTrue(grid.isValidMove(new BoardPosition(4, 5), PlayerColor.BLACK), "Le coup (4, 5) devrait être valide pour BLACK");
        assertTrue(grid.isValidMove(new BoardPosition(5, 4), PlayerColor.BLACK), "Le coup (5, 4) devrait être valide pour BLACK");

        assertFalse(grid.isValidMove(new BoardPosition(3, 3), PlayerColor.BLACK), "Le coup (3, 3) ne devrait pas être valide car il est occupé");
        assertFalse(grid.isValidMove(new BoardPosition(0, 0), PlayerColor.BLACK), "Le coup (0, 0) ne devrait pas être valide car il n'y a pas de Pawns à retourner");
    }

    @Test
    public void testFindValidMoves() {
        Grid grid = new Grid();
        List<BoardPosition> validMoves = grid.findValidMoves(PlayerColor.BLACK);

        assertEquals(4, validMoves.size(), "Il devrait y avoir 4 coups valides pour BLACK au départ");

        assertTrue(validMoves.contains(new BoardPosition(2, 3)), "Le coup (2, 3) devrait être valide");
        assertTrue(validMoves.contains(new BoardPosition(3, 2)), "Le coup (3, 2) devrait être valide");
        assertTrue(validMoves.contains(new BoardPosition(4, 5)), "Le coup (4, 5) devrait être valide");
        assertTrue(validMoves.contains(new BoardPosition(5, 4)), "Le coup (5, 4) devrait être valide");
    }

    @Test
    void testGetCapturablePawnsWithValidCapture() {
        Grid grid = new Grid();
        grid.initializeGrid();

        grid.placePawn(new BoardPosition(3, 3), new Pawn(PlayerColor.WHITE));
        grid.placePawn(new BoardPosition(3, 4), new Pawn(PlayerColor.BLACK));

        List<BoardPosition> capturablePawns = grid.getCapturablePawns(
                new BoardPosition(3, 2),
                Direction.RIGHT,
                PlayerColor.BLACK
        );

        assertEquals(1, capturablePawns.size(), "Devrait identifier un pion capturable");
        assertEquals(new BoardPosition(3, 3), capturablePawns.getFirst(),
                "Le pion capturable devrait être à la position (3,3)");
    }

    @Test
    void testGetCapturablePawns() {
        Grid grid = new Grid();
        grid.initializeGrid();
        grid.placePawn(new BoardPosition(2, 3), new Pawn(PlayerColor.BLACK));
        List<BoardPosition> capturablePawns = grid.getCapturablePawns(
                new BoardPosition(3, 3),
                Direction.RIGHT,
                PlayerColor.BLACK
        );
        assertEquals(0, capturablePawns.size(), "Aucun pion ne devrait être capturable dans cette direction");
    }

    @Test
    public void testFlipPion() {
        Grid grid = new Grid();
        grid.initializeGrid();

        grid.placePawn(new BoardPosition(2, 3), new Pawn(PlayerColor.BLACK));

        assertEquals(PlayerColor.BLACK, grid.getPawn(new BoardPosition(2, 3)).getColor(),
                "Le pion à (2, 3) devrait être noir après le placement");
    }
}