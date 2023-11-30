package cz.muni.fi.pb162.project.exceptions;

/**
 * @author Michaela Kecskesova
 */
public class NotAllowedMoveException extends Exception {
    /**
     * No-parameter constructor
     */
    public NotAllowedMoveException() {
        super();
    }

    /**
     * Constructor with message parameter
     * @param s message of the exception
     */
    public NotAllowedMoveException(String s) {
        super(s);
    }
}
