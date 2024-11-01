package fr.univ_amu.m1info.board_game_library;

import java.util.ArrayList;
import java.util.List;


public class Grid {
    // Tableau 2D de Piece représentant le plateau de jeu 8x8
    private Pion[][] squares;

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
    /*
       public void placePion(int x, int y, Pion pion) {
           if (isValidMove(x, y)) {
               squares[x][y] = pion;
           }
       }
   */
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

    public List<int[]> findValidMoves(Color playerColor) {
        List<int[]> validMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isValidMoveForOthello(i, j, playerColor)) {
                    validMoves.add(new int[]{i, j});
                }
            }
        }
        return validMoves;
    }

    private boolean isValidMoveForOthello(int x, int y, Color playerColor) {
        if (squares[x][y] != null) return false; // Case déjà occupée

        // Directions de recherche : haut, bas, gauche, droite, et diagonales
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] direction : directions) {
            int dx = direction[0], dy = direction[1];
            int row = x + dx, col = y + dy;
            boolean foundOpponent = false;

            while (row >= 0 && row < 8 && col >= 0 && col < 8) {
                Pion current = squares[row][col];
                if (current == null) break;

                if (current.getColor() != playerColor) {
                    foundOpponent = true;
                } else {
                    if (foundOpponent) return true; // Coup valide car un pion adverse est encerclé
                    break;
                }
                row += dx;
                col += dy;
            }
        }
        return false;
    }


    // Place un pion sur la grille
    public void placePion(int x, int y, Pion pion) {
        List<int[]> validMoves = findValidMoves(pion.getColor());    // liste des mouvements valides pour le joueur actuel.
        boolean isMoveValid = false;

        for (int[] move : validMoves) {
            if (move[0] == x && move[1] == y) {                 // vérifie si le mouvement est valide
                isMoveValid = true;
                break;
            }
        }

        if (isMoveValid) {
            squares[x][y] = pion;
        } else {
            System.out.println("Invalid move!");
        }
    }

}