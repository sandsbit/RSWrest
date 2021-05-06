package me.nikitaserba.rsw.parser;

import manifold.ext.rt.api.Jailbreak;
import manifold.rt.api.util.Pair;
import me.nikitaserba.rsw.parser.repsonses.ParsedWordInText;
import me.nikitaserba.rsw.parser.repsonses.TextParsingResult;
import me.nikitaserba.rsw.parser.repsonses.WordParsingResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class HomographParserGlobalTests {

    @Jailbreak HomographParser parser = null;

    @Test
    @DisplayName("Test that dictionaries are properly loaded")
    void TestLoadingDictionary() {
        Map<String, List<Homograph>> dictionaries = parser.homographs;
        assertEquals(1, dictionaries.size());
        assertTrue(dictionaries.containsKey("ru-RU"));
        assertEquals(2, dictionaries.get("ru-RU").size());
    }

    @Test
    @DisplayName("Test parsing words with default settings")
    void TestParsingWordsDefaultSettings() {
        String word = "пчелы";
        WordParsingResult result = HomographParser.parseWord(word);

        WordParsingResult expected = new WordParsingResult(word, true,
                new HashSet<String>(Arrays.asList("пчёлы", "пчелы́")), parser.defaultSettings);

        assertEquals(expected, result);

        assertFalse(HomographParser.parseWord("капибара").hasHomoforms());
    }

    @Test
    @DisplayName("Test parsing words ignoring diacritic")
    void TestParsingWordsIgnoreDiacritic() {
        String word = "замок";
        HomographParser.ParserSettings settings = new HomographParser.ParserSettings(false, "ru-RU");
        WordParsingResult result = HomographParser.parseWord(word, settings);

        WordParsingResult expected = new WordParsingResult(word, true,
                new HashSet<String>(Arrays.asList("за́мок", "замо́к")), settings);

        assertEquals(expected, result);

        assertFalse(HomographParser.parseWord("капибара", settings).hasHomoforms());
        assertFalse(HomographParser.parseWord("пчелы", settings).hasHomoforms());
    }

    @Test
    @DisplayName("Test parsing words with wrong language")
    void TestParsingWordsWrongLanguage() {
        HomographParser.ParserSettings settings = new HomographParser.ParserSettings(false, "ru-US");

        assertFalse(HomographParser.parseWord("замок", settings).hasHomoforms());
        assertFalse(HomographParser.parseWord("капибара", settings).hasHomoforms());
        assertFalse(HomographParser.parseWord("пчелы", settings).hasHomoforms());
    }

    @Test
    @DisplayName("Test splitting text into words")
    void TestSplittingTextIntoWords() {
        String text = " I  don't have any words   in; English.У меня еСТь ёж. Українська мова,та espáñol. ";
        assert text.length() == 83;

        Map<Pair<Integer, Integer>, String> expectedResult = new HashMap<>(15);
        expectedResult.put(new Pair<>(2, 2), "I");
        expectedResult.put(new Pair<>(5, 9), "don't");
        expectedResult.put(new Pair<>(11, 14), "have");
        expectedResult.put(new Pair<>(16, 18), "any");
        expectedResult.put(new Pair<>(20, 24), "words");
        expectedResult.put(new Pair<>(28, 29), "in");
        expectedResult.put(new Pair<>(32, 38), "English");
        expectedResult.put(new Pair<>(40, 40), "У");
        expectedResult.put(new Pair<>(42, 45), "меня");
        expectedResult.put(new Pair<>(47, 50), "еСТь");
        expectedResult.put(new Pair<>(52, 53), "ёж");
        expectedResult.put(new Pair<>(56, 65), "Українська");
        expectedResult.put(new Pair<>(67, 70), "мова");
        expectedResult.put(new Pair<>(72, 73), "та");
        expectedResult.put(new Pair<>(75, 81), "espáñol");

        assertEquals(expectedResult, parser.splitStringIntoWords(text));
    }

    @Test
    @DisplayName("Test parsing text using default settings")
    void TestFindingHomographsInText() {
        String text = "В поле замок, в нём пЧЕлы, перед ними замо́к";
        TextParsingResult result = HomographParser.parseText(text);

        List<ParsedWordInText> parsedWordsExpected = new ArrayList<>();
        parsedWordsExpected.add(new ParsedWordInText("замок",
                new HashSet<String>(Arrays.asList("за́мок", "замо́к")),
                parser.defaultSettings,
                8, 12));
        parsedWordsExpected.add(new ParsedWordInText("пЧЕлы",
                new HashSet<String>(Arrays.asList("пчёлы", "пчелы́")),
                parser.defaultSettings,
                21, 25));
        TextParsingResult expectedResult = new TextParsingResult(text, parsedWordsExpected, parser.defaultSettings);

        assertEquals(expectedResult, result);

        assertFalse(HomographParser.parseText("Вот за́мок у пчелы́ хорош!").haveFoundAnyWordsThatHaveHomoforms());
        assertNull(HomographParser.parseText("Вот за́мок у пчелы́ хорош!").getWordsThatHaveHomoforms());
    }

    @Test
    @DisplayName("Test parsing text using not default settings")
    void TestFindingHomographsInTextNotIgnoreDiacritic() {
        String text = "В поле замок, в нём пЧЕлы, перед ними замо́к";
        HomographParser.ParserSettings settings = new HomographParser.ParserSettings(false, "ru-RU");
        TextParsingResult result = HomographParser.parseText(text, settings);

        List<ParsedWordInText> parsedWordsExpected = new ArrayList<>();
        parsedWordsExpected.add(new ParsedWordInText("замок",
                new HashSet<String>(Arrays.asList("за́мок", "замо́к")),
                settings,
                8, 12));
        TextParsingResult expectedResult = new TextParsingResult(text, parsedWordsExpected,
                settings);

        assertEquals(expectedResult, result);

        assertFalse(HomographParser.parseText("Вот за́мок у пчелы хорош!", settings).haveFoundAnyWordsThatHaveHomoforms());
    }
}
