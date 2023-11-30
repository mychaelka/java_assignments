package cz.muni.fi.pb162.hw01.impl.displays;


public class Stringifier implements DisplayStringifier {

    /**
     * Check whether instance of Display can be stringified
     * @param display display to check
     * @return true if display can be stringified, otherwise false
     */
    @Override
    public boolean canStringify(Display display) {
        return display instanceof MultisegmentDisplay;
    }

    /**
     * Get the display as array of line segments
     * @param display display to stringify
     * @return array of display segments (first element corresponds to top row)
     */
    @Override
    public String[] asLines(Display display) {

        if (!canStringify(display)) {
            return null;
        }

        MultisegmentDisplay multidisplay = (MultisegmentDisplay) display;
        char[] displayText = multidisplay.getText();

        StringBuilder[] builders = {new StringBuilder(), new StringBuilder(), new StringBuilder()};
        for (int i = 0; i < multidisplay.getSize(); ++i) {
            builders[0].append(topRow(displayText[i]));
            builders[1].append(middleRow(displayText[i]));
            builders[2].append(bottomRow(displayText[i]));
        }
        return new String[]{builders[0].toString(), builders[1].toString(), builders[2].toString()};
    }

    /**
     * Get instance of display as a three-segment String
     * @param display display to stringify
     * @return three-segment representation of display
     */
    @Override
    public String asString(Display display) {
        String[] outString = asLines(display);
        return String.join("\n", outString);
    }

    /**
     * Get top segment of a number
     * @param number number to be stringified
     * @return top segment of the number
     */
    private String topRow(int number) {
        String outputString;

        switch (number) {
            case '1', '4', '6', ' ' -> outputString = "   ";
            default -> outputString = " _ ";
        }

        return outputString;
    }

    /**
     * Get middle segment of a number
     * @param number number to be stringified
     * @return middle segment of the number
     */
    private String middleRow(int number) {
        String outputString;

        switch (number) {
            case '0' -> outputString = "| |";
            case '1', '7' -> outputString = "  |";
            case '2', '3' -> outputString = " _|";
            case '4','8', '9' -> outputString = "|_|";
            case ' ' -> outputString = "   ";
            default -> outputString = "|_ ";
        }
        return outputString;
    }

    /**
     * Get bottom segment of a number
     * @param number number to be stringified
     * @return bottom segment of the number
     */
    private String bottomRow(int number) {
        String outputString;

        switch (number) {
            case '0', '6', '8' -> outputString = "|_|";
            case '1', '4', '7', '9' -> outputString = "  |";
            case '3', '5' -> outputString = " _|";
            case ' ' -> outputString = "   ";
            default -> outputString = "|_ ";
        }
        return outputString;
    }
}

