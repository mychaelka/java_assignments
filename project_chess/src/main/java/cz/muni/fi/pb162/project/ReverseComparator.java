package cz.muni.fi.pb162.project;

import java.util.Comparator;

/**
 * @author Michaela Kecskesova
 */
public class ReverseComparator implements Comparator<Coordinates> {
    @Override
    public int compare(Coordinates c1, Coordinates c2) {

        if (c1.letterNumber() < c2.letterNumber()) {
            return 1;
        }
        if (c1.letterNumber() > c2.letterNumber()) {
            return -1;
        }

        return Integer.compare(c2.number(), c1.number());

    }
}
