package cz.muni.fi.pb162.hw01.impl.app;

import cz.muni.fi.pb162.hw01.cmd.Application;
import cz.muni.fi.pb162.hw01.impl.Factory;
import cz.muni.fi.pb162.hw01.impl.displays.DisplayStringifier;
import cz.muni.fi.pb162.hw01.impl.displays.MultisegmentDisplay;


/**
 * Display application
 */
public class DisplayApp implements Application<DisplayAppOptions> {

    /**
     * Runtime logic of the application
     *
     * @param options command line options
     * @return exit status code
     */
    public int run(DisplayAppOptions options) {
        Factory factory = new Factory();
        MultisegmentDisplay display = (MultisegmentDisplay) factory.display(options.getSize());
        display.set(options.getText());
        DisplayStringifier stringifier = factory.stringifier();
        System.out.println(stringifier.asString(display));

        return 0;
    }
}
