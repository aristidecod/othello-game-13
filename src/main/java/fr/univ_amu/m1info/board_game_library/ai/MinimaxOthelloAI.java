package fr.univ_amu.m1info.board_game_library.ai;

import fr.univ_amu.m1info.board_game_library.model.BoardPosition;
import fr.univ_amu.m1info.board_game_library.model.Game;
import fr.univ_amu.m1info.board_game_library.model.PlayerColor;
import fr.univ_amu.m1info.board_game_library.iterator.GridIterator;

/**
 * Implémentation de l'intelligence artificielle pour le jeu Othello
 * utilisant l'algorithme Minimax avec élagage alpha-beta
 */
public class MinimaxOthelloAI implements OthelloAI {
    // Augmenté à 5 pour une meilleure analyse
    final int MAX_DEPTH = 5;
    private static final int BOARD_SIZE = 8;
    private static final int TIMEOUT_MS = 2000;
    private long startTime;
    int bestScore;

    /**
     * Trouve le meilleur coup à jouer pour l'IA
     *
     * @param game Etat actuel du jeu
     * @param aiColor la couleur des pions de l'IA
     * @return le meilleur coup à jouer
     */
    @Override
    public BoardPosition findBestMove(Game game, PlayerColor aiColor) {
        startTime = System.currentTimeMillis();
        BoardPosition bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        // Parcours tous les coups valides possibles
        for (BoardPosition move : game.getValidMoves()) {
            Game gameCopy = game.clone();
            gameCopy.makeMove(move);
            int score = minimax(gameCopy, MAX_DEPTH, alpha, beta, false, aiColor);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
            alpha = bestScore;

            if (isTimeOut()) {
                break;
            }
        }

        return bestMove;
    }

    private boolean isTimeOut() {
        return System.currentTimeMillis() - startTime > TIMEOUT_MS;
    }

    /**
     * Implémentation de l'algorithme Minimax avec élagage alpha-beta
     */
    private int minimax(Game game, int depth, int alpha, int beta, boolean isMaximizing, PlayerColor aiColor) {
        if (depth == 0 || game.isOver() || isTimeOut()) {
            return evaluate(game, aiColor);
        }

        var validMoves = game.getValidMoves();
        if (validMoves.isEmpty()) {
            return evaluate(game, aiColor);
        }


        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (BoardPosition move : validMoves) {
                Game gameCopy = game.clone();
                gameCopy.makeMove(move);
                int score = minimax(gameCopy, depth - 1, alpha, beta, false, aiColor);
                bestScore = Math.max(score, bestScore);
                alpha = Math.max(alpha, score);
                if (beta <= alpha || isTimeOut()) break;
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (BoardPosition move : validMoves) {
                Game gameCopy = game.clone();
                gameCopy.makeMove(move);
                int score = minimax(gameCopy, depth - 1, alpha, beta, true, aiColor);
                bestScore = Math.min(score, bestScore);
                beta = Math.min(beta, score);
                if (beta <= alpha || isTimeOut()) break;
            }
        }
        return bestScore;
    }

    /**
     * Fonction d'évaluation améliorée de l'état du jeu
     */
    private int evaluate(Game game, PlayerColor aiColor) {
        int score = 0;
        GridIterator iterator = new GridIterator(BOARD_SIZE);

        // Matrice de poids pour l'évaluation des positions
        int[][] weights = {
                {100, -20, 10, 5, 5, 10, -20, 100},
                {-20, -50, -2, -2, -2, -2, -50, -20},
                {10, -2, 1, 1, 1, 1, -2, 10},
                {5, -2, 1, 1, 1, 1, -2, 5},
                {5, -2, 1, 1, 1, 1, -2, 5},
                {10, -2, 1, 1, 1, 1, -2, 10},
                {-20, -50, -2, -2, -2, -2, -50, -20},
                {100, -20, 10, 5, 5, 10, -20, 100}
        };

        while (iterator.hasNext()) {
            BoardPosition pos = iterator.next();
            var pawn = game.getGrid().getPawn(pos);
            if (pawn != null) {
                int weight = weights[pos.row()][pos.col()];
                if (pawn.getColor() == aiColor) {
                    score += weight;
                } else {
                    score -= weight;
                }
            }
        }
        return score;
    }
}