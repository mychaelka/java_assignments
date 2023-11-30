package cz.muni.fi.pb162.project;

/**
 * @author Michaela Kecskesova
 */
public class Draughts extends Game {
    private static final int BOARD_SIZE = 8;
    /**
     * Create a new instance of Game
     *
     * @param playerOne first player
     * @param playerTwo second player
     */
    public Draughts(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo);
    }

    /**
     * Update current status of the game
     */
    @Override
    protected void updateStatus() {
        Piece[] pieces = super.getBoard().getAllPiecesFromBoard();
        int blackCount = 0;
        int whiteCount = 0;

        for (Piece piece : pieces) {
            if (piece.getColor() == Color.WHITE) {
                whiteCount++;
            } else {
                blackCount++;
            }
        }

        if (whiteCount == 0) {
            setStateOfGame(StateOfGame.BLACK_PLAYER_WIN);
            return;
        }
        if (blackCount == 0) {
            setStateOfGame(StateOfGame.WHITE_PLAYER_WIN);
        }
    }

    /**
     * Set initial set for one player
     * @param color color of the pieces
     */
    private void setOnePlayerSet(Color color) {
        int startColumn = 5;

        if (color == Color.WHITE) {
            startColumn = 0;
        }

        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = startColumn; j < startColumn + 3; ++j) {
                if (j % 2 == i % 2) {
                    putPieceOnBoard(i, j, new Piece(color, PieceType.DRAUGHTS_MAN));
                }
            }
        }

    }

    /**
     * Set initial state of the board
     */
    @Override
    public void setInitialSet() {
        setOnePlayerSet(Color.WHITE);
        setOnePlayerSet(Color.BLACK);
    }

    /**
     * Make a move
     * @param coordsFrom initial coordinates
     * @param coordsTo new coordinates
     */
    @Override
    public void move(Coordinates coordsFrom, Coordinates coordsTo) {
        super.move(coordsFrom, coordsTo);

        Piece current = this.getBoard().getPiece(coordsTo.letterNumber(), coordsTo.number());

        // promotion
        if (current.getPieceType() == PieceType.DRAUGHTS_MAN) {
            if (current.getColor() == Color.WHITE && coordsTo.number() == 7) {
                putPieceOnBoard(coordsTo.letterNumber(), 7, new Piece(Color.WHITE, PieceType.DRAUGHTS_KING));
            }

            if (current.getColor() == Color.BLACK && coordsTo.number() == 0) {
                putPieceOnBoard(coordsTo.letterNumber(), 0, new Piece(Color.BLACK, PieceType.DRAUGHTS_KING));
            }
        }
    }

}
