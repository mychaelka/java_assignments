package cz.muni.fi.pb162.project.exceptions;

/**
 * @author Michaela Kecskesova
 */
public class InvalidFormatOfInputException extends RuntimeException {
    /**
     * No-parameter constructor
     */
    public InvalidFormatOfInputException() {
        super();
    }

    /**
     * Constructor with message parameter
     * @param s message of the exception
     */
    public InvalidFormatOfInputException(String s) {
        super(s);
    }
}
