package cz.muni.fi.pb162.project;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Michaela Kecskesova
 */
public enum PieceType {
    KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN, DRAUGHTS_KING, DRAUGHTS_MAN;

    private static final Map<Pair<PieceType, Color>, String> MAP = new HashMap<>();

    /**
     * Initialize the mapping of piece types to UNICODE icons
     */
    private static void initializeMap() {
        MAP.put(Pair.of(PieceType.KING, Color.WHITE), "♔");
        MAP.put(Pair.of(PieceType.QUEEN, Color.WHITE), "♕");
        MAP.put(Pair.of(PieceType.QUEEN, Color.WHITE), "♕");
        MAP.put(Pair.of(PieceType.BISHOP, Color.WHITE), "♗");
        MAP.put(Pair.of(PieceType.ROOK, Color.WHITE), "♖");
        MAP.put(Pair.of(PieceType.KNIGHT, Color.WHITE), "♘");
        MAP.put(Pair.of(PieceType.PAWN, Color.WHITE), "♙");
        MAP.put(Pair.of(PieceType.KING, Color.BLACK), "♚");
        MAP.put(Pair.of(PieceType.QUEEN, Color.BLACK), "♛");
        MAP.put(Pair.of(PieceType.BISHOP, Color.BLACK), "♝");
        MAP.put(Pair.of(PieceType.ROOK, Color.BLACK), "♜");
        MAP.put(Pair.of(PieceType.KNIGHT, Color.BLACK), "♞");
        MAP.put(Pair.of(PieceType.PAWN, Color.BLACK), "♟");
        MAP.put(Pair.of(PieceType.DRAUGHTS_MAN, Color.WHITE), "⛀");
        MAP.put(Pair.of(PieceType.DRAUGHTS_KING, Color.WHITE), "⛁");
        MAP.put(Pair.of(PieceType.DRAUGHTS_MAN, Color.BLACK), "⛂");
        MAP.put(Pair.of(PieceType.DRAUGHTS_KING, Color.BLACK), "⛃");
    }


    /**
     * Get the UNICODE icon for the current piece type
     * @param color color of the piece
     * @return corresponding UNICODE icon
     */
    public String getSymbol(Color color) {
        initializeMap();
        String symbol = MAP.get(Pair.of(this, color));

        if (symbol == null) {
            return " ";
        }

        return symbol;
    }
}
