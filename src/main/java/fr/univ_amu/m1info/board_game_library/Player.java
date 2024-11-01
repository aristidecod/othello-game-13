package fr.univ_amu.m1info.board_game_library;

public class Player {
    private final String name;
    private final Color color;
    private int score;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean play(int x, int y, Grid grid) {
        // Vérifie si le mouvement est valide dans la grille
        if (grid.isValidMove(x, y)) {
            // Place un pion de la couleur du joueur à une position valide
            grid.placePion(x, y, new Pion(color));
            return true;
        }
        return false;
    }

    /**
     * Calcule et met à jour le score du joueur en fonction du nombre de pions qu'il possède sur la grille.
     * @param grid La grille du jeu.
     * @return Le score calculé.
     */
    public int calculateScore(Grid grid) {
        int newScore = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Pion pion = grid.getPion(row, col);
                if (pion != null && pion.getColor() == this.color) {
                    newScore++;
                }
            }
        }
        this.score = newScore;
        return newScore;
    }
}
