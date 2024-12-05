package fr.univ_amu.m1info.board_game_library.model;

import fr.univ_amu.m1info.board_game_library.iterator.GridIterator;
import fr.univ_amu.m1info.board_game_library.iterator.BoardIterator;

import java.util.ArrayList;
import java.util.List;

public class Grid implements Cloneable {
    private Pawn[][] squares;

    public Grid() {
        squares = new Pawn[8][8];
            initializeGrid();
    }

    @Override
    public Grid clone() {
        try {
            Grid clonedGrid = (Grid) super.clone();
            clonedGrid.squares = new Pawn[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (squares[i][j] != null) {
                        clonedGrid.squares[i][j] = new Pawn(squares[i][j].getColor());
                    }
                }
            }
            return clonedGrid;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Should not happen");
        }
    }

    /**
     * Initialise la grille de jeu pour une partie d'Othello.
     * Place the four pions de départ au centre du plateau.
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
     * @param position La position sur le plateau
     * @param playerColor La couleur du joueur actuel (pion qui vient d'être placé).
     */
    public void flipPion(BoardPosition position, PlayerColor playerColor) {
        for (Direction direction : Direction.values()) {
            List<BoardPosition> toFlip = getCapturablePawns(position, direction, playerColor);
            for (BoardPosition posToFlip : toFlip) {
                squares[posToFlip.row()][posToFlip.col()].setColor(playerColor); // Retourne les pions capturés
            }
        }
    }

    /**
     * Récupère la liste des pions capturables dans une direction donnée.
     * @param position La position de départ
     * @param direction La direction à explorer
     * @param playerColor La couleur du joueur actuel
     * @return Une liste des positions des pions capturables dans cette direction
     */
    public List<BoardPosition> getCapturablePawns(BoardPosition position, Direction direction, PlayerColor playerColor) {
        List<BoardPosition> capturablePawns = new ArrayList<>();
        int row = position.row() + direction.getDx();
        int col = position.col() + direction.getDy();
        boolean foundOpponent = false;

        while (row >= 0 && row < 8 && col >= 0 && col < 8) {
            Pawn current = squares[row][col];
            if (current == null) {
                return new ArrayList<>();
            }
            if (current.getColor() != playerColor) {
                foundOpponent = true;
                capturablePawns.add(new BoardPosition(row, col));
            } else {
                return foundOpponent ? capturablePawns : new ArrayList<>();
            }
            row += direction.getDx();
            col += direction.getDy();
        }
        return new ArrayList<>();
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
     * @param position La position sur le plateau
     * @return Le pion à la position donnée
     */
    public Pawn getPawn(BoardPosition position) {
        if (position.row() >= 0 && position.row() < 8 && position.col() >= 0 && position.col() < 8) {
            return squares[position.row()][position.col()];
        }
        return null;
    }

    /**
     * Recherche les mouvements valides pour un joueur donné.
     * @param playerColor La couleur du joueur.
     * @return Une liste de coordonnées des mouvements valides.
     */
    public List<BoardPosition> findValidMoves(PlayerColor playerColor) {
        List<BoardPosition> validMoves = new ArrayList<>();
        BoardIterator iterator = new GridIterator(8);
        while (iterator.hasNext()) {
            BoardPosition position = iterator.next();
            if (isValidMove(position, playerColor)) {
                validMoves.add(position);
            }
        }
        return validMoves;
    }

    /**
     * Vérifie si un mouvement est valide pour un joueur donné.
     * @param position La position à vérifier
     * @param playerColor La couleur du joueur
     * @return true si le mouvement est valide, sinon false
     */
    public boolean isValidMove(BoardPosition position, PlayerColor playerColor) {
        if (squares[position.row()][position.col()] != null) return false;

        // Vérifie si le pion peut capturer des pions adverses dans une direction
        for (Direction direction : Direction.values()) {
            int row = position.row() + direction.getDx();
            int col = position.col() + direction.getDy();
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
     * @param position La position où placer le pion sur le plateau
     * @param pawn Le pion à placer sur la position
     * @return true si le mouvement est valide et le pion est placé, false si le mouvement est invalide
     */
    public boolean placePawn(BoardPosition position, Pawn pawn) {
        if (isValidMove(position, pawn.getColor())) {
            squares[position.row()][position.col()] = pawn;
            flipPion(position, pawn.getColor());
            return true;
        }
        return false;
    }


}
