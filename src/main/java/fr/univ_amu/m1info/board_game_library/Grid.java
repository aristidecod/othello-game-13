package fr.univ_amu.m1info.board_game_library;

import java.util.ArrayList;
import java.util.List;


public class Grid {
    // Tableau 2D de Piece représentant le plateau de jeu 8x8
    private Pawn[][] squares;

    public Grid() {
        squares = new Pawn[8][8];
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
        squares[3][3] = new Pawn(PlayerColor.WHITE);
        squares[4][4] = new Pawn(PlayerColor.WHITE);
        // Pions noirs
        squares[3][4] = new Pawn(PlayerColor.BLACK);
        squares[4][3] = new Pawn(PlayerColor.BLACK);
    }

    public void flipPion(int x, int y) {
        if (squares[x][y] != null) {
            Pawn pawn = (Pawn) squares[x][y];
            // Change la couleur du pion
            if (pawn.getColor() == PlayerColor.BLACK) {
                pawn.setColor(PlayerColor.WHITE);
            } else {
                pawn.setColor(PlayerColor.BLACK);
            }
        }
    }

    public void displayGrid() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (squares[i][j] == null) {
                    System.out.print("- ");
                } else {
                    Pawn pawn = (Pawn) squares[i][j];
                    System.out.print(pawn.getColor() == PlayerColor.BLACK ? "B " : "W ");
                }
            }
            System.out.println();
        }
    }

    public Pawn getPawn(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return squares[x][y];
        }
        return null;
    }

    public List<int[]> findValidMoves(PlayerColor playerColor) {
        List<int[]> validMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isValidMove(i, j, playerColor)) {
                    validMoves.add(new int[]{i, j});
                }
            }
        }
        return validMoves;
    }

    public boolean isValidMove(int x, int y, PlayerColor playerColor) {
        if (squares[x][y] != null) return false;

        for (Direction direction : Direction.values()) {
            int row = x + direction.getDx();
            int col = y + direction.getDy();
            boolean foundOpponent = false;

            while (row >= 0 && row < 8 && col >= 0 && col < 8) {
                Pawn current = squares[row][col];
                if (current == null) break;

                if (current.getColor() != playerColor) {
                    foundOpponent = true;
                } else {
                    if (foundOpponent) return true;
                    break;
                }
                row += direction.getDx();
                col += direction.getDy();
            }
        }
        return false;
    }


    // Place un pion sur la grille
    public boolean placePawn(int x, int y, Pawn pawn) {
        List<int[]> validMoves = findValidMoves(pawn.getColor());    // liste des mouvements valides pour le joueur actuel.
        boolean isMoveValid = false;

        for (int[] move : validMoves) {
            if (move[0] == x && move[1] == y) {                 // vérifie si le mouvement est valide
                isMoveValid = true;
                break;
            }
        }

        if (isMoveValid) {
            squares[x][y] = pawn;
            return true;
        } else {
            return false;
        }
    }
}