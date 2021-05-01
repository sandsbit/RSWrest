package me.nikitaserba.rsw.parser;

import me.nikitaserba.rsw.parser.repsonses.TextParsingResult;
import me.nikitaserba.rsw.parser.repsonses.WordParsingResult;

import java.util.List;
import java.util.Map;

/**
 * Class that can be used to check if word has homoforms
 * or to find words that have homoforms in text.
 */
public final class HomographParser {


    // ---- GENERAL PART ----

    public static class ParserSettings {

        public boolean ignoreDiacritic;

        public ParserSettings(boolean ignoreDiacritic) {
            this.ignoreDiacritic = ignoreDiacritic;
        }
    }

    // Settings that will be used when user don't pass his own
    public static final ParserSettings defaultSettings = new ParserSettings(true);  // TODO: Read defaults from file

    // List of all homograps parsed from dictionary. Is loaded from resources in static context
    private static List<Homograph> homographs;

    /**
     * Load homographs from resources.
     */
    private static void loadHomographs() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    static {
        loadHomographs();
    }

    /**
     * Check if the word has homoforms.
     *
     * @param word - word to check.
     * @param settings - settings to be used by parser.
     * @return WordParsingResult instance with results of parsing.
     */
    public static WordParsingResult parseWord(String word, ParserSettings settings) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Check what words in the text have homoforms.
     *
     * @param text - text to check.
     * @param settings - settings to be used by parser.
     * @return TextParsingResult with results of parsing.
     */
    public static TextParsingResult parseText(String text, ParserSettings settings) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Check if the word has homoforms using default settings.
     *
     * @param word - word to check.
     * @return WordParsingResult instance with results of parsing.
     */
    public static WordParsingResult parseWord(String word) {
        return parseWord(word, defaultSettings);
    }

    /**
     * Check what words in the text have homoforms using default settings.
     *
     * @param text - text to check.
     * @return TextParsingResult with results of parsing.
     */
    public static TextParsingResult parseText(String text) {
        return parseText(text, defaultSettings);
    }

    // ---- SESSION PART ----

    private static List<Map<Long, String>> sessions;  // stores text currently associated with session by its id.
    private static List<Map<Long, ParserSettings>> settings;  // stores settings associated with session by its id.

    /**
     * Start new session.
     *
     * Session should be used to store setting and to optimize analyzing
     * text after small edits by user.
     *
     * @return session id.
     */
    public static Long startSession() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * End session.
     *
     * @param id - session id.
     */
    public static void endSession(Long id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Set or change settings associated with session.
     *
     * @param id - session id.
     * @param settings - new settings.
     */
    public static void setSessionSettings(Long id, ParserSettings settings) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Get settings associated with session. Returns default settings if there's no
     * settings associated with given session.
     *
     * @param id - session id
     * @return settings asscoiated with session with id = `id`
     */
    public static ParserSettings getSessionSettings(Long id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Check what words in the text have homoforms.
     *
     * Uses settings associated with given session.
     *
     * @param id - session id.
     * @param word - word to check.
     * @return WordParsingResult instance with results of parsing.
     */
    public static WordParsingResult s_parseWord(Long id, String word) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Check what words in the text have homoforms using default settings.
     *
     * Uses settings associated with given session.
     *
     * @param id - session id.
     * @param text - text to check.
     * @return TextParsingResult with results of parsing.
     */
    public static TextParsingResult s_parseText(Long id, String text) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}
