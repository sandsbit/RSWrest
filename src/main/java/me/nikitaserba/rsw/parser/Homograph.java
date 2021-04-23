package me.nikitaserba.rsw.parser;

/**
 * Class that represents homograph parsed from dictionary.
 */
public class Homograph {

    private String word;
    private HomographType type;
    private boolean isIgnoringDiacritic;  // True if word have homoforms only ignoring diacritical marks

}
