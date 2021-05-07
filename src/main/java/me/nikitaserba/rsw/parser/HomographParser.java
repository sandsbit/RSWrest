package me.nikitaserba.rsw.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import manifold.rt.api.util.Pair;
import me.nikitaserba.rsw.parser.repsonses.ParsedWordInText;
import me.nikitaserba.rsw.parser.repsonses.Session;
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

    private HomographParser() {}

    // ---- GENERAL PART ----

    public static final class ParserSettings {

        public boolean ignoreDiacritic;
        public String languageCode;

        public ParserSettings(boolean ignoreDiacritic, String languageCode) {
            this.ignoreDiacritic = ignoreDiacritic;
            this.languageCode = languageCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ParserSettings that = (ParserSettings) o;
            return ignoreDiacritic == that.ignoreDiacritic && languageCode.equals(that.languageCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ignoreDiacritic, languageCode);
        }
    }

    // Settings that will be used when user don't pass his own
    public static final ParserSettings defaultSettings = new ParserSettings(true,"ru-RU");  // TODO: Read defaults from file

    // List of all homograps parsed from dictionary. Is loaded from resources in static context.
    // Is sorted by language code
    private static Map<String, List<Homograph>> homographs;
    private static final Type homographType = new TypeToken<ArrayList<Homograph>>(){}.getType();

    /**
     * Parses filenames' of all available dictionaries in resources and returns them as an array of Resources.
     *
     * @return Array of `org.springframework.core.io.Resource` representing all available dictionaries.
     * @throws IOException if there were any errors while working with resources.
     */
    private static Resource[] getAllDictionariesAsResources() throws IOException {
        ClassLoader classLoader = HomographParser.class.getClassLoader();
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver(classLoader);
        return patternResolver.getResources("dictionaries/*.json");
    }

    /**
     * Loads dictionary from InputStream, parses it as json and saves to `homographs` map.
     *
     * @param languageCode - language code of that dictionary that is parsed from name of dictionary's file.
     * @param in - input stream to read from.
     */
    private static void loadDictionaryFromStream(String languageCode, InputStream in) {
        List<Homograph> homographsFromJson;
        homographsFromJson = new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), homographType);
        homographs.put(languageCode, homographsFromJson);
    }

    /**
     * Remove '.json. extension from filename.
     *
     * If filename does not have '.json' extension behavior in undefined.
     *
     * @param filename - filename to proceed.
     * @return same string as passed without '.json' extension.
     */
    private static String removeJsonExtension(@NonNull String filename) {
        assert filename.endsWith(".json");
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

    /**
     * Find dictionary entry for given word in given dictionary.
     *
     * @param word - word to search for.
     * @param dictionary - dictionary to search in.
     * @return dictionary entry for given word in given dictionary or `null` if there's no such entries.
     */
    private static Homograph findWordInDictionary(String word, List<Homograph> dictionary) {
        if (dictionary != null)
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
     * Split text into words ifnoring all spaces, digits and punctuantion.
     * Also saves positions of all words in original string.
     *
     * @param text - text to split into words.
     * @return map; every word is stored under key which is the pair of positions of the first and last letters of the
     *         word in the original string counting from 1.
     */
    private static Map<Pair<Integer, Integer>, String> splitStringIntoWords(String text) {
        Map<Pair<Integer, Integer>, String> result = new HashMap<>();
        boolean parsingWord = false;
        StringBuilder currentWord = new StringBuilder();
        int wordBeginning = -1;  // starting from 1 for first letter
        for (int i = 0; i < text.length(); ++i) {
            char letter = text.charAt(i);
            boolean currentLetterApostropheAndNextIsNot = (letter == '\'' || letter == '`')
                    && Character.isAlphabetic(text.charAt(i+1));
            boolean currentLetterIsCombiningAcuteAccent = letter == 769;
            if (Character.isAlphabetic(letter) || currentLetterApostropheAndNextIsNot || currentLetterIsCombiningAcuteAccent) {
                if (!parsingWord) {
                    currentWord.setLength(0);
                    wordBeginning = (i + 1);
                    parsingWord = true;
                }
                currentWord.append(letter);
            } else if (parsingWord) {
                int endPosition = i;

                result.put(new Pair<>(wordBeginning, endPosition), currentWord.toString());
                parsingWord = false;
            }
        }
        return result;
    }

    /**
     * It's a functional interface that represents method that parses word.
     */
    interface WordParserFunctionalInterface {
        public WordParsingResult execute(String word, ParserSettings settings);
    }

    /**
     * Parses text using given word parser.
     *
     * @param text - text to check.
     * @param settings - settings to be used by parser.
     * @param wordParser - function or lambda that will parse words.
     * @return TextParsingResult with results of parsing.
     */
    private static TextParsingResult parseTextWithGivenWordParsingMethod(String text, ParserSettings settings,
                                                                         WordParserFunctionalInterface wordParser) {
        Map<Pair<Integer, Integer>, String> words = splitStringIntoWords(text);
        List<ParsedWordInText> result = new ArrayList<>();
        words.forEach((pos, word) -> {
            WordParsingResult wordParsingResult = wordParser.execute(word, settings);
            if (wordParsingResult.hasHomoforms())
                result.add(new ParsedWordInText(wordParsingResult, pos.getFirst(), pos.getSecond()));
        });

        return new TextParsingResult(text, result, settings);

    }

    /**
     * Check what words in the text have homoforms.
     *
     * @param text - text to check.
     * @param settings - settings to be used by parser.
     * @return TextParsingResult with results of parsing.
     */
    public static TextParsingResult parseText(String text, ParserSettings settings) {
        return parseTextWithGivenWordParsingMethod(text, settings, HomographParser::parseWord);
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

    /**
     * Start new session.
     *
     * Session should be used to store setting and to optimize analyzing
     * text after small edits by user.
     *
     * @return session id.
     */
    public static String startSession() {
        return Session.create().getToken();
    }

    /**
     * End session.
     *
     * @param id - session id.
     */
    public static void endSession(String id) {
        Session.delete(id);
    }

    /**
     * Set or change settings associated with session.
     *
     * @param id - session id.
     * @param settings - new settings.
     */
    public static void setSessionSettings(String id, ParserSettings settings) {
        Session.getByToken(id).setSettings(settings);
    }

    /**
     * Get settings associated with session. Returns default settings if there's no
     * settings associated with given session.
     *
     * @param id - session id
     * @return settings asscoiated with session with id = `id`
     */
    public static ParserSettings getSessionSettings(String id) {
        return Session.getByToken(id).getSettings();
    }

    enum ChangeState {
        CHANGED,
        NOT_CHANGED
    }

    /**
     * Parses word using cache and settings from given session.
     *
     * @param session - session to use.
     * @param word - word to check.
     * @param saveWord - if it is true, last parsed word field in session will be updated, otherwise not.
     * @return WordParsingResult instance with results of parsing is key; value contains information if parsing
     *         result has changed since last request.     */
    private static Pair<WordParsingResult, ChangeState> parseWordUsingCache(Session session, String word, boolean saveWord) {
        WordParsingResult result = session.getWordParsingResultFromCache(word);
        if (result == null) {
            result = parseWord(word, session.getSettings());
            session.addWordParsingResultToCache(word, result);
        }
        boolean changed = !session.getWord().equals(word);

        if (saveWord)
            session.setWord(word);

        return new Pair<>(result, changed ? ChangeState.CHANGED : ChangeState.NOT_CHANGED);
    }

    /**
     * Check what word has homoforms.
     *
     * Uses settings associated with given session.
     *
     * @param id - session id.
     * @param word - word to check.
     * @return WordParsingResult instance with results of parsing is key; value contains information if parsing
     *         result has changed since last request.
     */
    public static Pair<WordParsingResult, ChangeState> s_parseWord(String id, String word) {
        return parseWordUsingCache(Session.getByToken(id), word, true);
    }

    /**
     * Check what words in the text have homoforms.
     *
     * Uses settings associated with given session.
     *
     * @param id - session id.
     * @param text - text to check.
     * @return TextParsingResult instance with results of parsing is key; value contains information if parsing
     *         result has changed since last request.
     */
    public static Pair<TextParsingResult, ChangeState> s_parseText(String id, String text) {
        Session session = Session.getByToken(id);
        if (session.getText().equals(text))
            return new Pair<>(session.getTextParsingResultCache(), ChangeState.NOT_CHANGED);

        session.setText(text);
        TextParsingResult result = parseTextWithGivenWordParsingMethod(text, session.getSettings(), (word, settings) -> {
            return parseWordUsingCache(session, word, false).getFirst();
        });
        session.setTextParsingResultCache(result);

        return new Pair<>(result, ChangeState.CHANGED);
    }

}
