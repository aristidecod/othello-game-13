package fr.univ_amu.m1info.board_game_library;

public class GridIterator implements BoardIterator {
    private final int size;
    private int currentRow;
    private int currentCol;

    public GridIterator(int size) {
        this.size = size;
        this.currentRow = 0;
        this.currentCol = 0;
    }

    @Override
    public boolean hasNext() {
        return currentRow < size && currentCol < size;
    }

    @Override
    public BoardPosition next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more positions in the grid");
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

    @Override
    public void reset() {
        currentRow = 0;
        currentCol = 0;
    }
}
