package cz.muni.fi.pb162.project;

/**
 * @author Michaela Kecskesova
 */
public enum Color {
    WHITE, BLACK;

    /**
     * Get opposite color of the current color
     * @return opposite color
     */
    public Color getOppositeColor() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
