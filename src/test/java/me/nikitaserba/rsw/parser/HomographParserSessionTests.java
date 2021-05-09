package me.nikitaserba.rsw.parser;

import manifold.ext.rt.api.Jailbreak;
import me.nikitaserba.rsw.parser.exceptions.InvalidSessionTokenException;
import me.nikitaserba.rsw.parser.repsonses.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HomographParserSessionTests {

    @Jailbreak HomographParser parser = null;

    @Test
    @DisplayName("Test creating, changing, getting and deleting session")
    void testSessions() {
        Session session = Session.create();
        assertNotNull(session);

        assertSame(session, Session.getByToken(session.getToken()));

        ParserSettings set1 = new ParserSettings(true, "en-US");
        ParserSettings set2 = new ParserSettings(true, "en-US");

        session.setSettings(set1);
        assertEquals(set2, session.getSettings());

        assertTrue(Session.delete(session.getToken()));
        assertFalse(Session.delete(session.getToken()));
    }

    @Test
    @DisplayName("Test creating, changing, getting and deleting session from HomographParser")
    void testSessionsHomographParser() throws InvalidSessionTokenException {
        String id = HomographParser.startSession();
        assertNotNull(id);

        ParserSettings set1 = new ParserSettings(true, "en-US");
        ParserSettings set2 = new ParserSettings(true, "en-US");

        HomographParser.setSessionSettings(id, set1);
        assertEquals(set2, HomographParser.getSessionSettings(id));
        HomographParser.setSessionSettingToDefault(id);
        assertNotEquals(set2, HomographParser.getSessionSettings(id));
        assertEquals(parser.defaultSettings, HomographParser.getSessionSettings(id));

        HomographParser.endSession(id);

        assertThrows(InvalidSessionTokenException.class, () -> HomographParser.setSessionSettings(id, set2));
        assertThrows(InvalidSessionTokenException.class, () -> HomographParser.getSessionSettings(id));
        assertThrows(InvalidSessionTokenException.class, () -> HomographParser.endSession(id));
    }

    @Test
    @DisplayName("Test null policies in method that sets session settings")
    void testSessionSettingsNullPolicies() throws InvalidSessionTokenException {
        String id = HomographParser.startSession();
        assertNotNull(id);

        ParserSettings set1 = new ParserSettings(!parser.defaultSettings.isIgnoringDiacritic(), "en-US");
        ParserSettings set2 = new ParserSettings(null, "en-US");

        HomographParser.setSessionSettings(id, set1);

        HomographParser.setSessionSettings(id, set2, HomographParser.SessionSettingsNullPolicies.DONT_CHANGE);
        assertEquals(set1.isIgnoringDiacritic(), HomographParser.getSessionSettings(id).isIgnoringDiacritic());

        HomographParser.setSessionSettings(id, set2, HomographParser.SessionSettingsNullPolicies.SET_TO_NULL);
        assertEquals(null, HomographParser.getSessionSettings(id).isIgnoringDiacritic());

        HomographParser.setSessionSettings(id, set2, HomographParser.SessionSettingsNullPolicies.SET_TO_DEFAULT);
        assertEquals(parser.defaultSettings.isIgnoringDiacritic(), HomographParser.getSessionSettings(id).isIgnoringDiacritic());

        HomographParser.endSession(id);
    }

    @Test
    @DisplayName("Test parsing words with default settings")
    void testParsingWordsDefaultSettings() throws InvalidSessionTokenException {
        String sessionId = HomographParser.startSession();
        String word = "пчелы";
        WordParsingResultSession result = HomographParser.s_parseWord(sessionId, word);

        assertEquals(HomographParser.ChangeState.CHANGED, result.getSecond());

        WordParsingResult expected = new WordParsingResult(word, true,
                new HashSet<>(Arrays.asList("пчёлы", "пчелы́")), parser.defaultSettings);

        assertEquals(expected, result.getFirst());

        result = HomographParser.s_parseWord(sessionId, word);

        assertEquals(HomographParser.ChangeState.NOT_CHANGED, result.getSecond());

        assertEquals(expected, result.getFirst());

        WordParsingResultSession result2 = HomographParser.s_parseWord(sessionId, "капибара");
        assertFalse(result2.getFirst().hasHomoforms());
        assertEquals(HomographParser.ChangeState.CHANGED, result2.getSecond());
        assertNull(result2.getFirst().getPossibleHomoforms());

        HomographParser.endSession(sessionId);

        assertThrows(InvalidSessionTokenException.class, () -> {
           HomographParser.s_parseWord(sessionId, "пчелы");
        });
    }

    @Test
    @DisplayName("Test parsing words not ignoring diacritic")
    void testParsingWordsNotIgnoreDiacritic() throws InvalidSessionTokenException {
        String sessionId = HomographParser.startSession();
        String word = "замок";

        ParserSettings settings = new ParserSettings(false, "ru-RU");
        HomographParser.setSessionSettings(sessionId, settings);

        WordParsingResultSession result = HomographParser.s_parseWord(sessionId, word);

        assertEquals(HomographParser.ChangeState.CHANGED, result.getSecond());

        WordParsingResult expected = new WordParsingResult(word, true,
                new HashSet<String>(Arrays.asList("за́мок", "замо́к")), settings);

        assertEquals(expected, result.getFirst());

        assertFalse(HomographParser.s_parseWord(sessionId, "капибара").getFirst().hasHomoforms());
        assertFalse(HomographParser.s_parseWord(sessionId, "пчелы").getFirst().hasHomoforms());

        HomographParser.endSession(sessionId);
    }

    @Test
    @DisplayName("Test parsing words with wrong language")
    void testParsingWordsWrongLanguage() throws InvalidSessionTokenException {
        String sessionId = HomographParser.startSession();

        ParserSettings settings = new ParserSettings(false, "ru-US");
        HomographParser.setSessionSettings(sessionId, settings);

        assertFalse(HomographParser.s_parseWord(sessionId, "замок").getFirst().hasHomoforms());
        assertFalse(HomographParser.s_parseWord(sessionId, "капибара").getFirst().hasHomoforms());
        assertFalse(HomographParser.s_parseWord(sessionId, "пчелы").getFirst().hasHomoforms());

        HomographParser.endSession(sessionId);
    }

    @Test
    @DisplayName("Test parsing text using default settings")
    void testFindingHomographsInText() throws InvalidSessionTokenException {
        String sessionId = HomographParser.startSession();

        String text = "В поле замок, в нём пЧЕлы, перед ними замо́к";
        TextParsingResultSession result = HomographParser.s_parseText(sessionId, text);

        assertEquals(HomographParser.ChangeState.CHANGED, result.getSecond());

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

        assertEquals(expectedResult, result.getFirst());

        result = HomographParser.s_parseText(sessionId, text);

        assertEquals(HomographParser.ChangeState.NOT_CHANGED, result.getSecond());

        assertEquals(expectedResult, result.getFirst());

        result = HomographParser.s_parseText(sessionId, "");

        assertEquals(HomographParser.ChangeState.CHANGED, result.getSecond());

        assertFalse(HomographParser.s_parseText(sessionId, "Вот за́мок у пчелы́ хорош!").getFirst().haveFoundAnyWordsThatHaveHomoforms());
        assertNull(HomographParser.s_parseText(sessionId, "Вот за́мок у пчелы́ хорош!").getFirst().getWordsThatHaveHomoforms());

        HomographParser.endSession(sessionId);

        assertThrows(InvalidSessionTokenException.class, () -> {
            HomographParser.s_parseText(sessionId, "пчелы");
        });
    }

    @Test
    @DisplayName("Test parsing text using not default settings")
    void testFindingHomographsInTextNotIgnoreDiacritic() throws InvalidSessionTokenException {
        String sessionId = HomographParser.startSession();

        String text = "В поле замок, в нём пЧЕлы, перед ними замо́к";

        ParserSettings settings = new ParserSettings(false, "ru-RU");
        HomographParser.setSessionSettings(sessionId, settings);

        TextParsingResultSession result = HomographParser.s_parseText(sessionId, text);

        assertEquals(HomographParser.ChangeState.CHANGED, result.getSecond());

        List<ParsedWordInText> parsedWordsExpected = new ArrayList<>();
        parsedWordsExpected.add(new ParsedWordInText("замок",
                new HashSet<String>(Arrays.asList("за́мок", "замо́к")),
                settings,
                8, 12));
        TextParsingResult expectedResult = new TextParsingResult(text, parsedWordsExpected,
                settings);

        assertEquals(expectedResult, result.getFirst());

        assertFalse(HomographParser.s_parseText(sessionId, "Вот за́мок у пчелы хорош!").getFirst().haveFoundAnyWordsThatHaveHomoforms());

        HomographParser.endSession(sessionId);
    }
}
