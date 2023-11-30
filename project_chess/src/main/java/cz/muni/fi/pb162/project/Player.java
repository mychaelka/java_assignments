package cz.muni.fi.pb162.project;

/**
 * record Player
 *
 * @param name name of the player
 * @param color color of the player's pieces
 *
 * @author Michaela Kecskesova
 */
public record Player(String name, Color color) {
    /**
     * Override toString method to return <name>-<color>
     * @return <name>-<color> string
     */
    @Override
    public String toString() {
        return name + "-" + color;
    }
}
