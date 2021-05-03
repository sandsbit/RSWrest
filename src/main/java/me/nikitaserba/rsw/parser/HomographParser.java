package me.nikitaserba.rsw.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.nikitaserba.rsw.parser.repsonses.TextParsingResult;
import me.nikitaserba.rsw.parser.repsonses.WordParsingResult;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Class that can be used to check if word has homoforms
 * or to find words that have homoforms in text.
 */
public final class HomographParser {


    // ---- GENERAL PART ----

    public static class ParserSettings {

        public boolean ignoreDiacritic;
        public String languageCode;

        public ParserSettings(boolean ignoreDiacritic, String languageCode) {
            this.ignoreDiacritic = ignoreDiacritic;
            this.languageCode = languageCode;
        }
    }

    // Settings that will be used when user don't pass his own
    public static final ParserSettings defaultSettings = new ParserSettings(true,"ru-RU");  // TODO: Read defaults from file

    // List of all homograps parsed from dictionary. Is loaded from resources in static context.
    // Is sorted by language code
    private static Map<String, List<Homograph>> homographs;
    private static final Type homographType = new TypeToken<ArrayList<Homograph>>(){}.getType();

    private static Resource[] getAllDictionariesAsResources() throws IOException {
        ClassLoader classLoader = HomographParser.class.getClassLoader();
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver(classLoader);
        return patternResolver.getResources("dictionaries/*.json");
    }

    private static void loadDictionaryFromStream(String languageCode, InputStream in) {
        List<Homograph> homographsFromJson;
        homographsFromJson = new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), homographType);
        homographs.put(languageCode, homographsFromJson);
    }

    private static String removeJsonExtension(@NonNull String filename) {
        return filename.substring(0, filename.length() - 5);
    }

    /**
     * Load homographs from resources.
     */
    private static void loadHomographs() {
        homographs = new HashMap<>();
        try {
            Resource[] dictionaries = getAllDictionariesAsResources();
            for (Resource dict : dictionaries)
                if (dict.isFile())
                    loadDictionaryFromStream(removeJsonExtension(Objects.requireNonNull(dict.getFilename())),
                            dict.getInputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static {
        loadHomographs();
    }

    private static Homograph findWordInDictionary(String word, List<Homograph> dictionary) {
        for (Homograph homograph : dictionary) {
            if (homograph.getWord().equals(word))
                return homograph;
        }
        return null;
    }

    /**
     * Check if the word has homoforms.
     *
     * @param word - word to check.
     * @param settings - settings to be used by parser.
     * @return WordParsingResult instance with results of parsing.
     */
    public static WordParsingResult parseWord(String word, ParserSettings settings) {
        boolean mayBeProperName = Character.isUpperCase(word.charAt(0));
        String wordLowered = word.toLowerCase(Locale.ROOT);

        finding_homoforms:
        {
            Homograph recordInDictionary = findWordInDictionary(wordLowered, homographs.get(settings.languageCode));
            if (recordInDictionary == null)
                break finding_homoforms;
            if (!settings.ignoreDiacritic && recordInDictionary.isIgnoringDiacritic())
                break finding_homoforms;
            if (!mayBeProperName && recordInDictionary.getType() == HomographType.PROPER_NAME)
                break finding_homoforms;

            return new WordParsingResult(word, true, recordInDictionary.getHomoforms(), settings);
        }

        return new WordParsingResult(word, false, null, settings);
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
