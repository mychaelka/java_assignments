package cz.muni.fi.pb162.project.utils;

import cz.muni.fi.pb162.project.Coordinates;

/**
 * @author Michaela Kecskesova
 */
public final class BoardNotation {
    private static final char A = 97;

    private BoardNotation() {}

    /**
     * Get notation of coordinates in <letterNumber><number> form.
     *
     * @param letterNumber row number
     * @param number column number
     * @return coordinates string in <letterNumber><number> form.
     */
    public static String getNotationOfCoordinates(int letterNumber, int number) {
        char letterNum = (char) (letterNumber + A);
        return Character.toString(letterNum) + Integer.toString(number + 1);
    }

    /**
     * Get numeric coordinates from <letterNumber><number> notation
     *
     * @param letterNumber row letter
     * @param number column number
     * @return Coordinates object with numeric coordinates.
     */
    public static Coordinates getCoordinatesOfNotation(char letterNumber, int number) {
        return new Coordinates(letterNumber - A, number - 1);
    }
}
