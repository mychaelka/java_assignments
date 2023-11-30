package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.exceptions.EmptySquareException;
import cz.muni.fi.pb162.project.exceptions.NotAllowedMoveException;

/**
 * @author Michaela Kecskesova
 */
public interface Playable extends Caretaker {
    /**
     * Set initial layout of the pieces on board
     */
    void setInitialSet();

    /**
     * Make a move
     * @param coordsFrom initial coordinates
     * @param coordsTo new coordinates
     */
    void move(Coordinates coordsFrom, Coordinates coordsTo);

    /**
     * Play the game
     */
    void play() throws EmptySquareException, NotAllowedMoveException;

    /**
     * Method is called when we want save Originator.
     */
    void hitSave();

    /**
     * Method is called when we want to take a step to back.
     */
    void hitUndo();
}
