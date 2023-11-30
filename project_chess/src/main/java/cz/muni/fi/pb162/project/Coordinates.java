package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.utils.BoardNotation;

/**
 * Record Coordinates
 *
 * @param letterNumber row number
 * @param number column number
 * @author Michaela Kecskesova
 */
public record Coordinates(int letterNumber, int number) implements Comparable<Coordinates> {

    /**
     * Override default string representation of Coordinates to
     * <letterNumber><number>.
     *
     * @return String representation in <letterNumber><number> format
     */
    @Override
    public String toString() {
        return BoardNotation.getNotationOfCoordinates(this.letterNumber, this.number);
    }

    /**
     * Create a new Coordinates object based on two coordinates.
     *
     * @param letterNumber letter coordinate
     * @param number number coordinate
     */
    public Coordinates(int letterNumber, int number) {
        this.letterNumber = letterNumber;
        this.number = number;
    }

    /**
     * Get average of two coordinates.
     *
     * @return average of the two coordinates.
     */
    public double averageOfCoordinates() {
        return (this.number + this.letterNumber) / 2.0;
    }

    /**
     * Adds two sets of coordinates.
     *
     * @return a new Coordinate object with the summed coordinates.
     */
    public Coordinates add(Coordinates newcoords) {
        return new Coordinates(this.letterNumber + newcoords.letterNumber(),
                this.number + newcoords.number());
    }

    @Override
    public int compareTo(Coordinates coords) {
        if (this.letterNumber() > coords.letterNumber()) {
            return 1;
        }
        if (this.letterNumber() < coords.letterNumber()) {
            return -1;
        }

        return Integer.compare(this.number(), coords.number());
    }
}
