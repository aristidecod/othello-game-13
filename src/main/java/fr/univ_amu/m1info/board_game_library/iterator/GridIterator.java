package fr.univ_amu.m1info.board_game_library.iterator;

import fr.univ_amu.m1info.board_game_library.model.BoardPosition;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GridIterator implements Iterator<BoardPosition> {
    private final int size;
    private int currentRow;
    private int currentCol;
    private static final int BOARD_SIZE = 8;

    public GridIterator(int size) {
        this.size = size;
        this.currentRow = 0;
        this.currentCol = 0;
    }

    /**
     * Vérifie s'il reste des positions à parcourir dans la grille
     * @return true s'il reste des positions à parcourir, false sinon
     */
    @Override
    public boolean hasNext() {
        return currentRow < size && currentCol < size;
    }

    /**
     * Retourne la position actuelle et avance à la position suivante
     * Le parcours se fait de gauche à droite et de haut en bas
     * @return La position actuelle sous forme d'un objet BoardPosition
     * @throws NoSuchElementException si on essaie d'avancer au-delà de la dernière position
     */
    @Override
    public BoardPosition next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more positions in the grid");
        }
        BoardPosition position = new BoardPosition(currentRow, currentCol);
        // Avancer à la prochaine position
        currentCol++;
        if (currentCol >= size) {
            currentCol = 0;
            currentRow++;
        }
        return position;
    }
    
    /**
     * Cette opération n'est pas supportée pour cet itérateur.
     * @throws UnsupportedOperationException cette opération n'est pas supportée
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove operation is not supported by this iterator");
    }
}
