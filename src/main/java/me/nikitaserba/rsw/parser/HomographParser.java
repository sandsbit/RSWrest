package me.nikitaserba.rsw.parser;

import me.nikitaserba.rsw.parser.repsonses.TextParsingResult;
import me.nikitaserba.rsw.parser.repsonses.WordParsingResult;

import java.util.List;
import java.util.Map;

public class HomographParser {


    // ---- GENERAL PART ----

    public static class ParserSettings {

        public boolean ignoreDiacritic;

        public ParserSettings(boolean ignoreDiacritic) {
            this.ignoreDiacritic = ignoreDiacritic;
        }
    }

    public static final ParserSettings defaultSettings = new ParserSettings(true);  // TODO: Read defaults from file

    private static List<Homograph> homographs;

    private static void loadHomographs() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    static {
        loadHomographs();
    }

    public static WordParsingResult parseWord(String word) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static TextParsingResult parseText(String text) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // ---- SESSION PART ----

    private static List<Map<Long, String>> sessions;

    public static Long startSession() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static void endSession() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static WordParsingResult s_parseWord(String word) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static TextParsingResult s_parseText(String text) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}
