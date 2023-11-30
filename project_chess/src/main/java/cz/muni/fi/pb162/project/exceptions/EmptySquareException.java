package cz.muni.fi.pb162.project.exceptions;

/**
 * @author Michaela Kecskesova
 */
public class EmptySquareException extends Exception {
    /**
     * No-parameter constructor
     */
    public EmptySquareException() {
        super();
    }

    /**
     * Constructor with message parameter
     * @param s message of the exception
     */
    public EmptySquareException(String s) {
        super(s);
    }
}
