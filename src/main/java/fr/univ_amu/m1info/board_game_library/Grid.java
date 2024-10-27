package fr.univ_amu.m1info.board_game_library;

public class Grid {
    // Tableau 2D de Piece représentant le plateau de jeu 8x8
    private Pion[][] squares;

    // Constructeur
    public Grid() {
        squares = new Pion[8][8];
        initializeGrid();
    }

    /**
     * Initialise la grille de jeu pour une partie d'Othello
     * Place les 4 pions de départ au centre du plateau
     */
    public void initializeGrid() {
        // On s'assure que la grille est vide
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = null;
            }
        }

        // Place les 4 pions de départ au centre
        // Pions blancs
        squares[3][3] = new Pion(Color.WHITE);
        squares[4][4] = new Pion(Color.WHITE);
        // Pions noirs
        squares[3][4] = new Pion(Color.BLACK);
        squares[4][3] = new Pion(Color.BLACK);
    }

    public boolean isValidMove(int x, int y) {
        // Vérifie si les coordonnées sont dans la grille
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            return false;
        }

        // Vérifie si la case est déjà occupée
        return squares[x][y] == null;
    }

    public void placePion(int x, int y, Pion pion) {
        if (isValidMove(x, y)) {
            squares[x][y] = pion;
        }
    }

    public void flipPion(int x, int y) {
        if (squares[x][y] != null) {
            Pion pion = (Pion) squares[x][y];
            // Change la couleur du pion
            if (pion.getColor() == Color.BLACK) {
                pion.setColor(Color.WHITE);
            } else {
                pion.setColor(Color.BLACK);
            }
        }
    }

    public void displayGrid() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (squares[i][j] == null) {
                    System.out.print("- ");
                } else {
                    Pion pion = (Pion) squares[i][j];
                    System.out.print(pion.getColor() == Color.BLACK ? "B " : "W ");
                }
            }
            System.out.println();
        }
    }

    public Pion getPion(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return squares[x][y];
        }
        return null;
    }
}