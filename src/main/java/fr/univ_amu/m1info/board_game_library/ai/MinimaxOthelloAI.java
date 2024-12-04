package fr.univ_amu.m1info.board_game_library.ai;

import fr.univ_amu.m1info.board_game_library.model.BoardPosition;
import fr.univ_amu.m1info.board_game_library.model.Game;
import fr.univ_amu.m1info.board_game_library.model.PlayerColor;
import fr.univ_amu.m1info.board_game_library.iterator.GridIterator;

/**
 * Implémentation de l'intelligence artificielle pour le jeu Othello
 * utilisant l'algorithme Minimax
 */
public class MinimaxOthelloAI implements OthelloAI {
    // Profondeur maximale de l'arbre de recherche
    private final int MAX_DEPTH = 4;
    private static final int BOARD_SIZE = 8;

    /**
     * Trouve le meilleur coup à jouer pour l'IA
     *
     * @param game    Etat actuel du jeu
     * @param aiColor la couleur des pions de  l'IA
     * @return le meilleur coup à jouer
     */
    @Override
    public BoardPosition findBestMove(Game game, PlayerColor aiColor) {
        BoardPosition bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        // Parcours tous les coups valides possibles
        for (BoardPosition move : game.getValidMoves()) {
            //crée une copie du jeu pour simuler le coup
            Game gameCopy = game.clone();
            gameCopy.makeMove(move);
            //calcule le score du coup en utilisant l'algorithme minimax
            int score = minimax(gameCopy, MAX_DEPTH, false, aiColor);
            // Met à jour le meilleur coup si le score est meilleur
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    /**
     * Implémentation de l'algorithme Minimax
     *
     * @param game         Etat actuel du jeu
     * @param depth        Profondeur de l'arbre de recherche
     * @param isMaximizing Indique si l'IA doit maximiser ou minimiser le score
     * @param aiColor      Couleur des pions de l'IA
     * @return le score du coup
     */
    private int minimax(Game game, int depth, boolean isMaximizing, PlayerColor aiColor) {
        // Si on atteint la profondeur maximale ou si la partie est terminée
        if (depth == 0 || game.isOver()) {
            return evaluate(game, aiColor);
        }

        if (isMaximizing) {
            //Trouve le meilleur coup pour l'IA : maximise le score
            int bestScore = Integer.MIN_VALUE;
            for (BoardPosition move : game.getValidMoves()) {
                Game gameCopy = game.clone();
                gameCopy.makeMove(move);
                int score = minimax(gameCopy, depth - 1, false, aiColor);
                bestScore = Math.max(score, bestScore);
            }
            return bestScore;
        } else {
            //Trouve le meilleur coup pour l'adversaire : minimise le score
            int bestScore = Integer.MAX_VALUE;
            for (BoardPosition move : game.getValidMoves()) {
                Game gameCopy = game.clone();
                gameCopy.makeMove(move);
                int score = minimax(gameCopy, depth - 1, true, aiColor);
                bestScore = Math.min(score, bestScore);
            }
            return bestScore;
        }
    }

    /**
     * Fonction d'évaluation de l'état du jeu
     *
     * @param game    Etat actuel du jeu
     * @param aiColor Couleur des pions de l'IA
     * @return le score de l'état du jeu
     */
    private int evaluate(Game game, PlayerColor aiColor) {
        int score = 0;
        GridIterator iterator = new GridIterator(BOARD_SIZE);

        while (iterator.hasNext()) {
            BoardPosition pos = iterator.next();
            var pawn = game.getGrid().getPawn(pos);
            if (pawn != null) {
                if (pawn.getColor() == aiColor) {
                    score++;
                } else {
                    score--;
                }
            }
        }
        return score;
    }
}