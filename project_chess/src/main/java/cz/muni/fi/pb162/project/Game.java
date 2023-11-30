package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.exceptions.EmptySquareException;
import cz.muni.fi.pb162.project.exceptions.InvalidFormatOfInputException;
import cz.muni.fi.pb162.project.exceptions.NotAllowedMoveException;
import cz.muni.fi.pb162.project.utils.BoardNotation;
import cz.muni.fi.pb162.project.utils.Memento;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;


/**
 * @author Michaela Kecskesova
 */
public abstract class Game implements Playable {
    private final Stack<Memento> mementoHistory;
    private Board board;

    private final Player playerOne;
    private final Player playerTwo;

    private StateOfGame stateOfGame;
    private static final Scanner SCANNER = new Scanner(System.in);


    /**
     * Update status of the game
     */
    protected abstract void updateStatus();

    /**
     * Create a new instance of Game
     * @param playerOne first player
     * @param playerTwo second player
     */
    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = new Board();
        this.stateOfGame = StateOfGame.PLAYING;
        this.mementoHistory = new Stack<Memento>();
    }

    /**
     * Get first player
     * @return player One
     */
    public Player getPlayerOne() {
        return this.playerOne;
    }

    /**
     * Get second player
     * @return player Two
     */
    public Player getPlayerTwo() {
        return this.playerTwo;
    }

    /**
     * Get game board
     * @return board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Get current state of game
     * @return state of game
     */
    public StateOfGame getStateOfGame() {
        return this.stateOfGame;
    }

    /**
     * Set current state of game
     * @param state state of game
     */
    public void setStateOfGame(StateOfGame state) {
        this.stateOfGame = state;
    }

    /**
     * Get player that is currently on the move
     * @return player on the move
     */
    public Player getCurrentPlayer() {
        if (this.board.getRound() % 2 == 0) {
            if (this.playerOne.color() == Color.WHITE) {
                return this.playerOne;
            }
            return this.playerTwo;
        }
        if (this.playerOne.color() == Color.WHITE) {
            return this.playerTwo;
        }
        return this.playerOne;

    }

    /**
     * Load input from player
     * @return Coordinates chosen by player
     */
    private Coordinates getInputFromPlayer() {
        var position = SCANNER.next().trim();
        var letterNumber = position.charAt(0);
        if (!Character.isDigit(position.charAt(1))) {
            throw new InvalidFormatOfInputException("Input is in invalid format");
        }
        var number = Integer.parseInt(String.valueOf(position.charAt(1)));
        return BoardNotation.getCoordinatesOfNotation(letterNumber, number);
    }

    /**
     * Put a piece on the board
     * @param letterNumber row number
     * @param number column number
     * @param piece piece to be put on the board
     */
    public void putPieceOnBoard(int letterNumber, int number, Piece piece) {
        this.board.putPieceOnBoard(letterNumber, number, piece);
    }

    /**
     * Make a move
     * @param coordsFrom initial coordinates
     * @param coordsTo new coordinates
     */
    public void move(Coordinates coordsFrom, Coordinates coordsTo) {
        Piece current = this.getBoard().getPiece(coordsFrom.letterNumber(), coordsFrom.number());

        if (current == null) {
            return;
        }

        putPieceOnBoard(coordsTo.letterNumber(), coordsTo.number(), current);
        putPieceOnBoard(coordsFrom.letterNumber(), coordsFrom.number(), null);
    }

    /**
     * Start playing the game
     */
    @Override
    public void play() throws EmptySquareException, NotAllowedMoveException {
        Coordinates moveTo;
        Coordinates moveFrom;
        while (stateOfGame == StateOfGame.PLAYING) {
            moveFrom = getInputFromPlayer();

            if (!Board.inRange(moveFrom)) {
                throw new EmptySquareException("Coordinates are not in range");
            } else if (this.board.isEmpty(moveFrom.letterNumber(), moveFrom.number())) {
                throw new EmptySquareException("Empty position");
            }

            moveTo = getInputFromPlayer();

            Set<Coordinates> possibleMoves = this.allPossibleMovesByCurrentPlayer();

            if (!possibleMoves.contains(moveTo)) {
                throw new NotAllowedMoveException("Move not allowed");
            }

            move(moveFrom, moveTo);
            updateStatus();
            this.board.setRound(this.board.getRound() + 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Game)) {
            return false;
        }

        return Objects.equals(this.getPlayerOne(), ((Game) o).getPlayerOne()) &&
                Objects.equals(getPlayerTwo(), ((Game) o).getPlayerTwo()) &&
                this.board.equals(((Game) o).board) &&
                this.stateOfGame == ((Game) o).getStateOfGame() &&
                this.mementoHistory.equals(((Game) o).mementoHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.playerOne, this.playerTwo, this.board, this.stateOfGame);
    }

    /**
     * Save the game
     */
    @Override
    public void hitSave() {
        mementoHistory.push((Memento) board.save());
    }

    /**
     * Undo last change
     */
    @Override
    public void hitUndo() {
        if (mementoHistory.empty()) {
            return;
        }
        board.restore(mementoHistory.pop());
    }

    public List<Memento> getMementoHistory() {
        return Collections.unmodifiableList(this.mementoHistory);
    }

    /**
     * Get all moves allowed for the current player
     * @return set of allowed moves
     */
    public Set<Coordinates> allPossibleMovesByCurrentPlayer() {
        Piece[] currentPlayerPieces = Arrays.stream(this.board.getAllPiecesFromBoard())
                .filter(p -> p.getColor() == getCurrentPlayer().color())
                .toArray(Piece[]::new);

        ReverseComparator comparator = new ReverseComparator();
        Set<Coordinates> possibleMoves = new TreeSet<>(comparator);

        for (Piece piece : currentPlayerPieces) {
            possibleMoves.addAll(piece.getAllPossibleMoves(this));
        }

        return possibleMoves;
    }
}
