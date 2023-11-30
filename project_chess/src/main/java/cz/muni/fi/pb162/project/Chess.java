package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.exceptions.MissingPlayerException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michaela Kecskesova
 */
public class Chess extends Game implements GameWritable {

    private static final int BOARD_SIZE = 8;

    /**
     * Create a new instance of Chess
     *
     * @param playerOne first player
     * @param playerTwo second player
     */
    public Chess(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo);
    }


    /**
     * Update current status of the game
     */
    @Override
    public void updateStatus() {
        Piece[] pieces = super.getBoard().getAllPiecesFromBoard();
        List<Piece> kings = Arrays.stream(pieces)
                .filter(s -> s.getPieceType() == PieceType.KING)
                .toList();

        if (kings.size() == 1) {
            switch (kings.get(0).getColor()) {
                case WHITE -> setStateOfGame(StateOfGame.WHITE_PLAYER_WIN);
                case BLACK -> setStateOfGame(StateOfGame.BLACK_PLAYER_WIN);
                default -> {
                    break;
                }
            }
            return;
        }

        setStateOfGame(StateOfGame.PLAYING);
    }

    /**
     * Set initial set for one player
     * @param color color of the pieces
     */
    private void setOnePlayerSet(Color color) {
        int column = 7;
        int pawnsColumn = 6;

        if (color == Color.WHITE) {
            column = 0;
            pawnsColumn = 1;
        }

        //set pawns
        for (int i = 0; i < BOARD_SIZE; ++i) {
            putPieceOnBoard(i, pawnsColumn, new Piece(color, PieceType.PAWN));
        }

        putPieceOnBoard(0, column, new Piece(color, PieceType.ROOK));
        putPieceOnBoard(7, column, new Piece(color, PieceType.ROOK));

        putPieceOnBoard(1, column, new Piece(color, PieceType.KNIGHT));
        putPieceOnBoard(6, column, new Piece(color, PieceType.KNIGHT));

        putPieceOnBoard(2, column, new Piece(color, PieceType.BISHOP));
        putPieceOnBoard(5, column, new Piece(color, PieceType.BISHOP));

        putPieceOnBoard(3, column, new Piece(color, PieceType.QUEEN));

        putPieceOnBoard(4, column, new Piece(color, PieceType.KING));

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
        if (current.getPieceType() == PieceType.PAWN) {
            if (current.getColor() == Color.WHITE && coordsTo.number() == 7) {
                putPieceOnBoard(coordsTo.letterNumber(), 7, new Piece(Color.WHITE, PieceType.QUEEN));
            }

            if (current.getColor() == Color.BLACK && coordsTo.number() == 0) {
                putPieceOnBoard(coordsTo.letterNumber(), 0, new Piece(Color.BLACK, PieceType.QUEEN));
            }
        }
    }

    @Override
    public void write(OutputStream os) throws IOException {
        String header = getHeader();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {
            writer.write(header);
            for (int row = 0; row < BOARD_SIZE; ++row) {
                StringBuilder line = new StringBuilder();
                for (int col = 0; col < BOARD_SIZE; ++col) {
                    Piece piece = this.getBoard().getPiece(new Coordinates(row, col));
                    if (piece == null) {
                        line.append('_');
                    } else {
                        line.append(piece.getPieceType()).append(',').append(piece.getColor());
                    }
                    if (col < BOARD_SIZE - 1) {
                        line.append(';');
                    }
                }
                line.append(System.lineSeparator());
                writer.write(line.toString());
            }
        }
    }

    @Override
    public void write(File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            write(outputStream);
        }
    }

    /**
     * Get header with players as string
     * @return header
     */
    private String getHeader() {
        return String.valueOf(getPlayerOne()) + ';' + getPlayerTwo() + System.lineSeparator();
    }

    /**
     * Builder class for Chess
     * @author Michaela Kecskesova
     */
    public static class Builder implements Buildable<Chess>, GameReadable {
        private static final char A = 97;
        private final List<Player> players;
        private final Board board;

        /**
         * Create Builder instance
         */
        public Builder() {
            this.players = new ArrayList<>();
            this.board = new Board();
        }
        
        @Override
        public Chess build() {
            if (players.size() != 2) {
                throw new MissingPlayerException("One or both players are missing");
            }

            Chess chess = new Chess(players.get(0), players.get(1));

            for (int i = 0; i < BOARD_SIZE; ++i) {
                for (int j = 0; j < BOARD_SIZE; ++j) {
                    chess.getBoard().putPieceOnBoard(i, j, this.board.getPiece(i, j));
                }
            }

            return chess;
        }

        /**
         * Add player to the game
         * @param player player to be added
         * @return intermediate instance of Builder
         */
        public Builder addPlayer(Player player) {
            if (players.size() == 2) {
                return this;
            }
            this.players.add(player);
            return this;
        }

        /**
         * Add piece to current board
         * @param piece piece to be added
         * @param letterNumber row letter number
         * @param number column number
         * @return intermediate instance of Builder
         */
        public Builder addPieceToBoard(Piece piece, char letterNumber, int number) {
            this.board.putPieceOnBoard(letterNumber - A, number, piece);
            return this;
        }

        @Override
        public Builder read(InputStream is) throws IOException {
            return this.read(is, false);
        }

        private Piece createPiece(String pieceString) throws IOException {
            if (pieceString.equals("_")) {
                return null;
            }
            String[] parts = pieceString.split(",");
            Color color;

            if (parts[1].equals("WHITE")) {
                color = Color.WHITE;
            } else if (parts[1].equals("BLACK")) {
                color = Color.BLACK;
            } else {
                throw new UnsupportedEncodingException("Wrong color!");
            }

            return switch (parts[0]) {
                case "PAWN" -> new Piece(color, PieceType.PAWN);
                case "ROOK" -> new Piece(color, PieceType.ROOK);
                case "KNIGHT" -> new Piece(color, PieceType.KNIGHT);
                case "BISHOP" -> new Piece(color, PieceType.BISHOP);
                case "QUEEN" -> new Piece(color, PieceType.QUEEN);
                case "KING" -> new Piece(color, PieceType.KING);
                default -> throw new UnsupportedEncodingException("Invalid data: " + parts[0]);
            };
        }

        @Override
        public Builder read(InputStream is, boolean hasHeader) throws IOException {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                if (hasHeader) {
                    String header = reader.readLine();
                    String[] players = header.split(";");
                    for (String player : players) {
                        this.addPlayer(parsePlayer(player));
                    }
                }
                int row = 0;
                String line = reader.readLine();
                while (line != null) {
                    String[] pieceStrings = line.split(";");
                    if (pieceStrings.length != BOARD_SIZE) {
                        throw new UnsupportedEncodingException("Wrong line length!");
                    }
                    for (int col = 0; col < BOARD_SIZE; col++) {
                        Piece piece = createPiece(pieceStrings[col]);
                        this.board.putPieceOnBoard(row, col, piece);
                    }
                    row++;
                    line = reader.readLine();
                }

                if (row != BOARD_SIZE) {
                    throw new UnsupportedEncodingException("Wrong number of rows!");
                }
            }

            return this;
        }

        private Player parsePlayer(String playerData) {
            String[] parts = playerData.split("-");
            Color color = parts[1].equals("WHITE") ? Color.WHITE : Color.BLACK;
            return new Player(parts[0], color);
        }

        @Override
        public Builder read(File file) throws IOException {
            try (InputStream inputStream = new FileInputStream(file)) {
                return this.read(inputStream);
            }
        }

        @Override
        public Builder read(File file, boolean hasHeader) throws IOException {
            try (InputStream inputStream = new FileInputStream(file)) {
                return this.read(inputStream, hasHeader);
            }
        }
    }
}
