package fr.univ_amu.m1info.board_game_library.model;

import fr.univ_amu.m1info.board_game_library.iterator.GridIterator;
import java.util.Iterator;

public class Player {
    private final String name;
    private final PlayerColor playerColor;
    private int score;

    public Player(String name, PlayerColor playerColor) {
        this.name = name;
        this.playerColor = playerColor;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getColor() {
        return playerColor;
    }

    public int getScore() {
        return score;
    }

    public boolean play(BoardPosition position, Grid grid) {
        // Vérifie si le mouvement est valide dans la grille
        if (grid.isValidMove(position, playerColor)) {
            // Place un pion de la couleur du joueur à une position valide
            return grid.placePawn(position, new Pawn(playerColor));
        }
        return false;
    }

    /**
     * Calcule et met à jour le score du joueur en fonction du nombre de pions qu'il possède sur la grille.
     * @param grid La grille du jeu.
     */
    public void calculateScore(Grid grid) {
        int newScore = 0;
        Iterator<BoardPosition> iterator = new GridIterator(8);
        while (iterator.hasNext()) {
            BoardPosition position = iterator.next();
            Pawn pawn = grid.getPawn(position);
            if (pawn != null && pawn.getColor() == this.playerColor) {
                newScore++;
            }
        }
        this.score = newScore;
    }
}
