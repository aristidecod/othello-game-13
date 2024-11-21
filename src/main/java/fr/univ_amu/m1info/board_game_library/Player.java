package fr.univ_amu.m1info.board_game_library;

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

    public void setScore(int score) {
        this.score = score;
    }

    public boolean play(int x, int y, Grid grid) {
        // Vérifie si le mouvement est valide dans la grille
        if (grid.isValidMove(x, y, playerColor)) {
            // Place un pion de la couleur du joueur à une position valide
            return grid.placePawn(x, y, new Pawn(playerColor));
        }
        return false;
    }

    /**
     * Calcule et met à jour le score du joueur en fonction du nombre de pions qu'il possède sur la grille.
     * @param grid La grille du jeu.
     */
    public void calculateScore(Grid grid) {
        int newScore = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Pawn pion = grid.getPawn(row, col);
                if (pion != null && pion.getColor() == this.playerColor) {
                    newScore++;
                }
            }
        }
        this.score = newScore;
    }
}
