package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.moves.Move;
import cz.muni.fi.pb162.project.moves.Diagonal;
import cz.muni.fi.pb162.project.moves.Jump;
import cz.muni.fi.pb162.project.moves.Knight;
import cz.muni.fi.pb162.project.moves.Pawn;
import cz.muni.fi.pb162.project.moves.Straight;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Class Piece.
 *
 * @author Michaela Kecskesova
 */
public final class Piece implements Prototype {

    private static final AtomicLong LONG_ID = new AtomicLong(0);
    private final long id;
    private final Color color;
    private final PieceType pieceType;
    private final List<Move> strategies;


    /**
     * Create a Piece object with a random ID.
     */
    public Piece(Color color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
        this.id = LONG_ID.getAndIncrement();
        this.strategies =
                switch (pieceType) {
                    case KING -> List.of(new Straight(1), new Diagonal(1));
                    case QUEEN -> List.of(new Straight(), new Diagonal());
                    case BISHOP -> List.of(new Diagonal());
                    case ROOK -> List.of(new Straight());
                    case KNIGHT -> List.of(new Knight());
                    case PAWN -> List.of(new Pawn());
                    case DRAUGHTS_KING -> List.of(new Diagonal(1), new Jump());
                    case DRAUGHTS_MAN -> List.of(new Diagonal(1, true), new Jump(true));
                    default -> throw new IllegalArgumentException("Unknown type in chess.");
                };
    }

    /**
     * Get color of the current piece
     * @return piece color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Get type of the current piece
     * @return piece type
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * Get ID of a piece object.
     *
     * @return ID of the current object
     */
    public long getId() {
        return this.id;
    }

    @Override
    public Piece makeClone() {
        return new Piece(this.color, this.pieceType);
    }

    @Override
    public String toString() {
        return this.pieceType.getSymbol(this.color);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece)) {
            return false;
        }

        return this.id == ((Piece) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    /**
     * Get a list of movement strategies
     * @return list of moves
     */
    public List<Move> getMoves() {
        return this.strategies;
    }

    /**
     * Get all possible target positions in the current game
     * @param game current game
     * @return unmodifiable set of target coordinates
     */
    //Set<Coordinates> getAllPossibleMoves(Game game) {
    //    Coordinates currentCoords = game.getBoard().findCoordinatesOfPieceById(this.getId());
    //    Set<Coordinates> possibleMoves = new HashSet<>();
    //    List<Move> pieceMoves = this.getMoves();

    //    for (Move move : pieceMoves) {
    //        possibleMoves.addAll(move.getAllowedMoves(game, currentCoords));
    //    }

    //    return Collections.unmodifiableSet(possibleMoves);
    //}
    public Set<Coordinates> getAllPossibleMoves(Game game) {
        Coordinates currentCoords = game.getBoard().findCoordinatesOfPieceById(this.getId());
        return this.strategies.stream()
                .map(move -> move.getAllowedMoves(game, currentCoords))
                .flatMap(Set::stream)
                .collect(Collectors.toUnmodifiableSet());
    }
}
