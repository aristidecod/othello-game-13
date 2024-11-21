package fr.univ_amu.m1info.board_game_library;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private final Pawn[][] squares;

    public Grid() {
        squares = new Pawn[8][8];
        initializeGrid();
    }
    /**
     * Initialise la grille de jeu pour une partie d'Othello.
     * Place les 4 pions de départ au centre du plateau.
     */
    public void initializeGrid() {
        // Réinitialise chaque case de la grille à null
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = null;
            }
        }
        // Place les 4 pions de départ au centre du plateau
        squares[3][3] = new Pawn(PlayerColor.WHITE);
        squares[4][4] = new Pawn(PlayerColor.WHITE);
        squares[3][4] = new Pawn(PlayerColor.BLACK);
        squares[4][3] = new Pawn(PlayerColor.BLACK);
    }


    /**
     * Retourne les pions capturés dans toutes les directions à partir d'une position donnée.
     * @param x La position en ligne.
     * @param y La position en colonne.
     * @param playerColor La couleur du joueur actuel (pion qui vient d'être placé).
     */
    public void flipPion(int x, int y, PlayerColor playerColor) {
        for (Direction direction : Direction.values()) {
            List<int[]> toFlip = getCapturablePawns(x, y, direction, playerColor);
            for (int[] coords : toFlip) {
                int row = coords[0];
                int col = coords[1];
                squares[row][col].setColor(playerColor); // Retourne les pions capturés
            }
        }
    }

    /**
     * Récupère la liste des pions capturables dans une direction donnée.
     * @param x La position en ligne de départ.
     * @param y La position en colonne de départ.
     * @param direction La direction à explorer.
     * @param playerColor La couleur du joueur actuel.
     * @return Une liste de coordonnées des pions capturables dans cette direction.
     */
    private List<int[]> getCapturablePawns(int x, int y, Direction direction, PlayerColor playerColor) {
        List<int[]> capturablePawns = new ArrayList<>();
        int row = x + direction.getDx();
        int col = y + direction.getDy();
        boolean foundOpponent = false;
        // Parcourt les cases dans la direction donnée
        while (row >= 0 && row < 8 && col >= 0 && col < 8) {
            Pawn current = squares[row][col];
            if (current == null) {
                return new ArrayList<>(); // Aucun pion capturé dans cette direction
            }
            if (current.getColor() != playerColor) {
                foundOpponent = true; // Pion adverse trouvé
                capturablePawns.add(new int[]{row, col});
            } else {
                //si on trouve un pion de la couleur du joueur, on retourne la liste des pions capturables
                return foundOpponent ? capturablePawns : new ArrayList<>();
            }
            row += direction.getDx();
            col += direction.getDy();
        }
        return new ArrayList<>(); // Aucun pion capturé dans cette direction
    }
    /**
     * Affiche la grille de jeu dans la console.
     * Les pions noirs sont représentés par 'B', les pions blancs par 'W', et les cases vides par '-'.
     */
    public void displayGrid() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (squares[i][j] == null) {
                    System.out.print("- ");
                } else {
                    Pawn pawn = squares[i][j];
                    System.out.print(pawn.getColor() == PlayerColor.BLACK ? "B " : "W ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Retourne le pion à une position donnée.
     * @param x La position en ligne.
     * @param y La position en colonne.
     * @return Le pion à la position donnée.
     */
    public Pawn getPawn(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return squares[x][y];
        }
        return null;
    }

    /**
     * Recherche les mouvements valides pour un joueur donné.
     * @param playerColor La couleur du joueur.
     * @return Une liste de coordonnées des mouvements valides.
     */
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

    /**
     * Vérifie si un mouvement est valide pour un joueur donné.
     * @param x La position en ligne.
     * @param y La position en colonne.
     * @param playerColor La couleur du joueur.
     * @return true si le mouvement est valide, sinon false.
     */

    public boolean isValidMove(int x, int y, PlayerColor playerColor) {
        if (squares[x][y] != null) return false;

        // Vérifie si le pion peut capturer des pions adverses dans une direction
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

    /**
     * Place un pion sur la grille et retourne les pions capturés si le mouvement est valide.
     * @param x La position en ligne.
     * @param y La position en colonne.
     * @param pawn Le pion à placer.
     * @return true si le mouvement est valide et le pion est placé, sinon false.
     */
    public boolean placePawn(int x, int y, Pawn pawn) {
        if (isValidMove(x, y, pawn.getColor())) {
            squares[x][y] = pawn;
            flipPion(x, y, pawn.getColor()); // Retourne les pions capturés après le placement
            return true;
        }
        return false;
    }
}
