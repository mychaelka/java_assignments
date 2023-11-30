package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.utils.Memento;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Michaela Kecskesova
 */
public class Board implements Originator {
    public static final int SIZE = 8;

    private final Piece[][] squares = new Piece[SIZE][SIZE];
    private int round;

    /**
     * Get number of the current round.
     *
     * @return current round
     */
    public int getRound() {
        return round;
    }

    /**
     * Set the round counter to the specified round.
     *
     * @param round current round
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Check whether a cell specified by row and column is on the board.
     *
     * @param row row number
     * @param col column number
     * @return true if the cell is in range, otherwise false
     */
    public static boolean inRange(int row, int col) {
        return (row < SIZE && row >= 0 && col < SIZE && col >= 0);
    }

    /**
     * Check whether a cell with specified coordinates is on the board.
     *
     * @param coords coordinates of the cell
     * @return true if the cell is in range, otherwise false.
     */
    public static boolean inRange(Coordinates coords) {
        return inRange(coords.letterNumber(), coords.number());
    }

    /**
     * Check whether a cell specified by coordinates is empty.
     *
     * @param row row number
     * @param col column number
     * @return true if the cell is empty or is outside the board range, false otherwise.
     */
    public boolean isEmpty(int row, int col) {
        if (!inRange(row, col)) {
            return true;
        }
        return this.squares[row][col] == null;
    }

    /**
     * Fetch a piece from the board.
     *
     * @param row row number
     * @param col column number
     * @return Piece currently on the specified position, null if
     * there is no piece or if specified position is outside the board.
     */
    public Piece getPiece(int row, int col) {
        if (isEmpty(row, col) || !inRange(row, col)) {
            return null;
        }
        return this.squares[row][col];
    }

    /**
     * Get piece on specified coordinates
     * @param coords coordinates
     * @return Piece instance
     */
    public Piece getPiece(Coordinates coords) {
        return getPiece(coords.letterNumber(), coords.number());
    }

    /**
     * Put a piece on the board.
     *
     * @param row row number
     * @param col column number
     * @param piece piece object to be put on the board.
     */
    public void putPieceOnBoard(int row, int col, Piece piece) {
        if (inRange(row, col)) {
            this.squares[row][col] = piece;
        }
    }

    /**
     * Find coordinates of a piece with specified ID
     *
     * @param id id of the piece
     * @return Coordinates of the piece if the piece is on board, null otherwise
     */
    public Coordinates findCoordinatesOfPieceById(long id) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                Piece currentPiece = getPiece(i, j);
                if (currentPiece != null && currentPiece.getId() == id) {
                    return new Coordinates(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Get all pieces that are currently on the board
     * @return array of pieces on the board
     */
    public Piece[] getAllPiecesFromBoard() {
        return Arrays.stream(this.squares)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .toArray(Piece[]::new);
    }

    /**
     * Print string representation of the board
     *
     * @return string representation of board
     */
    public String toString() {
        char rowNumber = 'A';
        String tileSeparator = " | ";
        StringBuilder builder = new StringBuilder();

        builder.append("  ");
        for (int j = 1; j < 9; ++j) {
            builder.append("  ").append(j).append(" ");
        }
        builder.append(System.lineSeparator());

        for (int i = 0; i < SIZE; ++i) {
            builder.append(rowSeparator(8));
            builder.append(System.lineSeparator());
            builder.append(rowNumber);

            for (int j = 0; j < SIZE; ++j) {
                builder.append(tileSeparator);
                if (getPiece(i, j) == null) {
                    builder.append(" ");
                } else {
                    builder.append(getPiece(i, j));
                }

                if (j == 7) {
                    builder.append(" |");
                }
            }
            builder.append(System.lineSeparator());
            rowNumber++;
        }
        builder.append(rowSeparator(8));
        return builder.toString();
    }

    /**
     * Helper method for printing row separator
     * @param length of line
     * @return rowSeparator
     */
    public String rowSeparator(int length) {
        return "  " + "----".repeat(Math.max(0, length));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Board)) {
            return false;
        }

        return this.round == ((Board) o).getRound() && Arrays.deepEquals(this.squares, ((Board) o).squares);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.round, Arrays.deepHashCode(this.squares));
    }

    /**
     * Save current board state
     * @return memento object
     */
    @Override
    public Object save() {
        Piece[][] savedBoard = new Piece[SIZE][SIZE];

        for (int i = 0; i < SIZE; ++i) {
            System.arraycopy(this.squares[i], 0, savedBoard[i], 0, SIZE);
        }

        return new Memento(savedBoard, this.getRound());
    }

    /**
     * Restore previous game
     * @param save last memorized state.
     */
    @Override
    public void restore(Object save) {
        Memento savedBoard = (Memento) save;
        this.setRound(savedBoard.getRound());
        System.arraycopy(savedBoard.getBoard(), 0, this.squares, 0, this.squares.length);
    }
}
