package cz.muni.fi.pb162.project;

/**
 * @author Michaela Kecskesova
 */
public enum StateOfGame {
    WHITE_PLAYER_WIN, BLACK_PLAYER_WIN, PAT, PLAYING;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
