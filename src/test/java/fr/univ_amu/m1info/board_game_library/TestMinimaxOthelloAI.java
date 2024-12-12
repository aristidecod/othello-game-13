package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.ai.MinimaxOthelloAI;
import fr.univ_amu.m1info.board_game_library.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMinimaxOthelloAI {

    private MinimaxOthelloAI minimaxAI;
    private Game game;

    @BeforeEach
    void setUp() {
        // Initialisation de l'IA et du jeu
        minimaxAI = new MinimaxOthelloAI();
        game = new Game();
        game.resetGame();
        game.setCurrentPlayer(game.getPlayer2()); // WHITE joue
    }

    @Test
    void testFindBestMove_WithValidMoves() {

        BoardPosition position1 = new BoardPosition(2, 3);
        BoardPosition position2 = new BoardPosition(4, 5);

        game.getGrid().placePawn(position1, new Pawn(PlayerColor.BLACK));
        game.getGrid().placePawn(position2, new Pawn(PlayerColor.WHITE));

        // Vérifier que des coups sont disponibles
        List<BoardPosition> validMoves = game.getValidMoves();
        assertFalse(validMoves.isEmpty(), "Il devrait y avoir des coups valides.");

        BoardPosition bestMove = minimaxAI.findBestMove(game, PlayerColor.WHITE);

        assertNotNull(bestMove, "Le meilleur coup ne doit pas être null.");
        assertTrue(validMoves.contains(bestMove), "Le meilleur coup doit faire partie des coups valides.");
    }

    @Test
    void testFindBestMove_WithLimitedDepth() {

        BoardPosition position1 = new BoardPosition(3, 4);
        BoardPosition position2 = new BoardPosition(4, 3);

        game.getGrid().placePawn(position1, new Pawn(PlayerColor.BLACK));
        game.getGrid().placePawn(position2, new Pawn(PlayerColor.WHITE));

        BoardPosition bestMove = minimaxAI.findBestMove(game, PlayerColor.WHITE);

        assertNotNull(bestMove, "L'IA devrait retourner un coup valide avec une faible profondeur.");
        System.out.println("Meilleur coup trouvé par l'IA (WHITE) : " + bestMove);
    }

    @Test
    void testEvaluate_EmptyGrid() {
        // Test de l'évaluation d'une grille vide
        int score = minimaxAI.evaluate(game, PlayerColor.WHITE);
        assertEquals(0, score, "Le score d'une grille vide doit être 0.");
    }


    @Test
    void testFindBestMove_NoValidMoves() {
        // Cas où il n'y a pas de coups valides
        Grid grid = new Grid();
        grid.placePawn(new BoardPosition(3, 3), new Pawn(PlayerColor.BLACK));
        grid.placePawn(new BoardPosition(3, 4), new Pawn(PlayerColor.BLACK));
        grid.placePawn(new BoardPosition(4, 3), new Pawn(PlayerColor.BLACK));
        grid.placePawn(new BoardPosition(4, 4), new Pawn(PlayerColor.BLACK));
        grid.placePawn(new BoardPosition(2, 3), new Pawn(PlayerColor.BLACK));
        grid.placePawn(new BoardPosition(2, 4), new Pawn(PlayerColor.BLACK));
        grid.placePawn(new BoardPosition(5, 3), new Pawn(PlayerColor.BLACK));
        grid.placePawn(new BoardPosition(5, 4), new Pawn(PlayerColor.BLACK));
        grid.placePawn(new BoardPosition(3, 2), new Pawn(PlayerColor.BLACK));
        grid.placePawn(new BoardPosition(4, 2), new Pawn(PlayerColor.BLACK));

        game.setGrid(grid);
        game.setCurrentPlayer(game.getPlayer2()); // WHITE joue

        // Vérifie qu'il n'y a pas de coups valides pour WHITE
        List<BoardPosition> validMoves = game.getValidMoves();
        assertTrue(validMoves.isEmpty(), "Il ne devrait pas y avoir de coups valides pour WHITE dans cette configuration.");

        BoardPosition bestMove = minimaxAI.findBestMove(game, PlayerColor.WHITE);

        assertNull(bestMove, "Le meilleur coup doit être null s'il n'y a pas de coups valides.");
    }

    @Test
    void testFindBestMove_Timeout() {
        // Test du timeout en réduisant artificiellement le temps
        MinimaxOthelloAI aiWithTimeout = new MinimaxOthelloAI() {
            @Override
            public boolean isTimeOut() {
                return true; // Forcer le timeout
            }
        };
        BoardPosition bestMove = aiWithTimeout.findBestMove(game, PlayerColor.WHITE);
        assertNull(bestMove, "Si un timeout se produit, le meilleur coup doit être null.");
    }


}
