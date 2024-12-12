package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Classe de test pour la classe Game
 * Cette classe vérifie toutes les fonctionnalités principales du jeu Othello
 */
public class TestGame {

    /**
     * Teste l'état initial du jeu après sa création
     * Vérifie que tous les composants essentiels sont correctement initialisés
     */
    @Test
    public void testInitialGameState() {
        Game game = new Game();

        // Vérification des composants principaux
        assertNotNull(game, "Le jeu ne devrait pas être null");
        assertNotNull(game.getGrid(), "La grille ne devrait pas être null");
        assertNotNull(game.getPlayer1(), "Le joueur 1 ne devrait pas être null");
        assertNotNull(game.getPlayer2(), "Le joueur 2 ne devrait pas être null");

        // Vérification du joueur initial
        assertEquals(game.getPlayer1(), game.getCurrentPlayer(),
                "Le joueur 1 (NOIR) devrait être le premier à jouer");
    }

    /**
     * Teste le mécanisme de changement de joueur
     * Vérifie que l'alternance entre les joueurs fonctionne correctement
     */
    @Test
    public void testSwitchPlayer() {
        Game game = new Game();
        Player initialPlayer = game.getCurrentPlayer();

        game.switchPlayer();
        assertNotEquals(initialPlayer, game.getCurrentPlayer(),
                "Le joueur courant devrait changer après switchPlayer");

        game.switchPlayer();
        assertEquals(initialPlayer, game.getCurrentPlayer(),
                "Le joueur courant devrait revenir au joueur initial après deux changements");
    }

    /**
     * Teste le mécanisme de clonage du jeu
     * Vérifie que le clone est une copie profonde du jeu original
     */
    @Test
    public void testGameClone() {
        Game game = new Game();
        Game clonedGame = game.clone();

        assertNotNull(clonedGame, "Le jeu cloné ne devrait pas être null");
        assertNotSame(game, clonedGame, "Le clone devrait être un nouvel objet");
        assertNotSame(game.getGrid(), clonedGame.getGrid(),
                "La grille clonée devrait être un nouvel objet");
        assertEquals(game.getCurrentPlayerName(), clonedGame.getCurrentPlayerName(),
                "Les joueurs courants devraient être identiques");
    }

    /**
     * Teste la validation des mouvements possibles
     * Vérifie que le jeu peut identifier correctement les coups valides
     */
    @Test
    public void testValidMoves() {
        Game game = new Game();
        List<BoardPosition> validMoves = game.getValidMoves();

        assertNotNull(validMoves, "La liste des mouvements valides ne devrait pas être null");
        assertFalse(validMoves.isEmpty(), "Il devrait y avoir des mouvements valides au début du jeu");
    }



    /**
     * Teste la détection de fin de partie
     * Vérifie différentes conditions de fin de jeu
     */
    @Test
    public void testGameOver() {
        Game game = new Game();

        // Au début du jeu
        assertFalse(game.isGameOver(), "Le jeu ne devrait pas être terminé au début");

        // Note:Pour tester une fin de partie réelle, il faudrait simuler une partie complète
    }

    /**
     * Teste les noms des joueurs et leur alternance
     */
    @Test
    public void testPlayerNames() {
        Game game = new Game();

        assertEquals("BLACK", game.getCurrentPlayerName(),
                "Le premier joueur devrait être BLACK");
        game.switchPlayer();
        assertEquals("WHITE", game.getCurrentPlayerName(),
                "Le second joueur devrait être WHITE");
    }

}