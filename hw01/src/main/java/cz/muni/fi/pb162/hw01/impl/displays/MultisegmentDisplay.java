package cz.muni.fi.pb162.hw01.impl.displays;

/**
 * Display class that is able to print multiple digits
 *
 * @author Michaela Kecskesova
 */
public class MultisegmentDisplay implements Display {
    private int size = 0;
    private final char[] displayText;  // stores character in ascii

    /**
     * Create an instance of MultisegmentDisplay
     * @param size size of the display
     */
    public MultisegmentDisplay(int size) {
        this.size = size;
        this.displayText = new char[size];
        for (int i = 0; i < size; ++i) {
            this.displayText[i] = ' ';
        }
    }

    /**
     * Set display to the given text
     * @param text text to be displayed
     */
    @Override
    public void set(String text) {
        set(0, text);
    }

    /**
     * Set display text starting on a specific position
     * @param pos starting position on the display
     * @param text text to be displayed
     */
    @Override
    public void set(int pos, String text) {
        int j = 0;
        for (int i = pos; i < size; ++i) {
            if (j >= text.length()) {
                this.displayText[i] = ' ';
            } else {
                this.displayText[i] = text.charAt(j);
            }
            ++j;
        }
    }

    /**
     * Clear the display
     */
    @Override
    public void clear() {
        for (int i = 0; i < this.size; ++i) {
            this.displayText[i] = ' ';
        }
    }

    /**
     * Clear specified position on the display
     * @param pos position to be cleared
     */
    @Override
    public void clear(int pos) {
        if (pos < 0 || pos >= this.size) {
            return;
        }
        this.displayText[pos] = ' ';
    }

    /**
     * Get the display text in form of array
     * @return display array
     */
    public char[] getText() {
        return this.displayText;
    }

    /**
     * Get the size of the display
     * @return display size
     */
    public int getSize() {
        return this.size;
    }
}
