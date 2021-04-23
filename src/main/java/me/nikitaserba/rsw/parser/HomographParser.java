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

    public static WordParsingResult parseWord(String word, ParserSettings settings) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static TextParsingResult parseText(String text, ParserSettings settings) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static WordParsingResult parseWord(String word) {
        return parseWord(word, defaultSettings);
    }

    public static TextParsingResult parseText(String text) {
        return parseText(text, defaultSettings);
    }

    // ---- SESSION PART ----

    private static List<Map<Long, String>> sessions;
    private static List<Map<Long, ParserSettings>> settings;

    public static Long startSession() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static void endSession(Long id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static void setSessionSettings(Long id, ParserSettings settings) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static ParserSettings getSessionSettings(Long id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static WordParsingResult s_parseWord(Long id, String word) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static TextParsingResult s_parseText(Long id, String text) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}
