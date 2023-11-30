package cz.muni.fi.pb162.project.utils;

import cz.muni.fi.pb162.project.Piece;

/**
 * @author Michaela Kecskesova
 * @param board current state of board
 * @param round current round
 */
public record Memento(Piece[][] board, int round) {
    public int getRound() {
        return round;
    }

    public Piece[][] getBoard() {
        return board;
    }
}
