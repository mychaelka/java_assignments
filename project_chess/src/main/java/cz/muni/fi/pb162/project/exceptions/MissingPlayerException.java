package cz.muni.fi.pb162.project.exceptions;

/**
 * @author Michaela Kecskesova
 */
public class MissingPlayerException extends RuntimeException{
    /**
     * No-parameter constructor
     */
    public MissingPlayerException() {
        super();
    }

    /**
     * Constructor with message parameter
     * @param s message of the exception
     */
    public MissingPlayerException(String s) {
        super(s);
    }
}
