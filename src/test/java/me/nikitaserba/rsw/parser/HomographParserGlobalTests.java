package me.nikitaserba.rsw.parser;

import manifold.ext.rt.api.Jailbreak;
import me.nikitaserba.rsw.parser.repsonses.WordParsingResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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
        WordParsingResult testBees = HomographParser.parseWord(word);

        assertEquals(word, testBees.getParsedWord());
        assertTrue(testBees.hasHomoforms());
        assertEquals(2, testBees.getPossibleHomoforms().size());
        assertTrue(testBees.getPossibleHomoforms().contains("пчёлы"));
        assertTrue(testBees.getPossibleHomoforms().contains("пчелы́"));
        assertFalse(testBees.getPossibleHomoforms().contains("пчелы"));
        assertEquals(testBees.getUsedSettings(), parser.defaultSettings);

        assertFalse(HomographParser.parseWord("капибара").hasHomoforms());
    }

    @Test
    @DisplayName("Test parsing words ignoring diacritic")
    void TestParsingWordsIgnoreDiacritic() {
        String word = "замок";
        HomographParser.ParserSettings settings = new HomographParser.ParserSettings(false, "ru-RU");
        WordParsingResult testBees = HomographParser.parseWord(word, settings);

        assertEquals(word, testBees.getParsedWord());
        assertTrue(testBees.hasHomoforms());
        assertEquals(2, testBees.getPossibleHomoforms().size());
        assertTrue(testBees.getPossibleHomoforms().contains("за́мок"));
        assertTrue(testBees.getPossibleHomoforms().contains("замо́к"));
        assertFalse(testBees.getPossibleHomoforms().contains("замок"));
        assertEquals(testBees.getUsedSettings(), settings);

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

}
