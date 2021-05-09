package me.nikitaserba.rsw.parser;

import me.nikitaserba.rsw.parser.repsonses.ParsedWordInText;
import me.nikitaserba.rsw.parser.repsonses.TextParsingResult;
import me.nikitaserba.rsw.parser.repsonses.WordParsingResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class DataClassesTests {
    @Test
    @DisplayName("Test equal() and hashCode() of Homograph class")
    void testHomographClass() {
        Homograph eq1 = new Homograph("сосиска", new HashSet<>(Arrays.asList("сОсиска", "сосИска")),
                HomographType.DIFFERENT_PART_OF_SPEECH, true);
        Homograph eq2 = new Homograph("сосиска", new HashSet<>(Arrays.asList("сосИска", "сОсиска")),
                HomographType.DIFFERENT_PART_OF_SPEECH, true);

        Homograph diff1 = new Homograph("не сосиска", new HashSet<>(Arrays.asList("сОсиска", "сосИска")),
                HomographType.DIFFERENT_PART_OF_SPEECH, true);
        Homograph diff2 = new Homograph("сосиска", new HashSet<>(Arrays.asList("сОсиска", "не сосИска")),
                HomographType.DIFFERENT_PART_OF_SPEECH, true);
        Homograph diff3 = new Homograph("сосиска", new HashSet<>(Arrays.asList("сОсиска", "сосИска")),
                HomographType.ONE_PART_OF_SPEECH, true);
        Homograph diff4 = new Homograph("сосиска", new HashSet<>(Arrays.asList("сОсиска", "сосИска")),
                HomographType.DIFFERENT_PART_OF_SPEECH, false);

        assert eq1 != eq2;
        assertEquals(eq1, eq2);

        assertNotEquals(eq1, diff1);
        assertNotEquals(eq1, diff2);
        assertNotEquals(eq1, diff3);
        assertNotEquals(eq1, diff4);
    }

    @Test
    @DisplayName("Test equal() and hashCode() of ParserSettings class")
    void testParserSettingsClass() {
        ParserSettings eq1 = new ParserSettings(true, "en-US");
        ParserSettings eq2 = new ParserSettings(true, "en-US");

        ParserSettings diff1 = new ParserSettings(false, "en-US");
        ParserSettings diff2 = new ParserSettings(true, "en-EN");

        assert eq1 != eq2;
        assertEquals(eq1, eq2);

        assertNotEquals(eq1, diff1);
        assertNotEquals(eq1, diff2);
    }

    @Test
    @DisplayName("Test equal() and hashCode() of WordParsingResult class")
    void testWordParsingResultClass() {
        ParserSettings set1 = new ParserSettings(true, "en-US");
        ParserSettings set2 = new ParserSettings(true, "en-US");
        ParserSettings set3 = new ParserSettings(false, "en-US");
        assert set1 != set2;
        assert set1.equals(set2);
        assert !set1.equals(set3);

        WordParsingResult eq1 = new WordParsingResult("сосиска", true,
                new HashSet<String>(Arrays.asList("сОсиска", "сосИска")), set1);
        WordParsingResult eq2 = new WordParsingResult("сосиска", true,
                new HashSet<String>(Arrays.asList("сосИска", "сОсиска")), set2);

        WordParsingResult diff1 = new WordParsingResult("yt сосиска", true,
                new HashSet<String>(Arrays.asList("сосИска", "сОсиска")), set2);
        WordParsingResult diff2 = new WordParsingResult("сосиска", true,
                new HashSet<String>(Arrays.asList("сосИска", "не сОсиска")), set2);
        WordParsingResult diff3 = new WordParsingResult("сосиска", false,
                null, set2);
        WordParsingResult diff4 = new WordParsingResult("сосиска", true,
                new HashSet<String>(Arrays.asList("сосИска", "сОсиска")), set3);

        assert eq1 != eq2;
        assertEquals(eq1, eq2);

        assertNotEquals(eq1, diff1);
        assertNotEquals(eq1, diff2);
        assertNotEquals(eq1, diff3);
        assertNotEquals(eq1, diff4);
    }

    @Test
    @DisplayName("Test equal() and hashCode() of ParsedWordInText class")
    void testParsedWordInTextClass() {
        ParserSettings set1 = new ParserSettings(true, "en-US");
        ParserSettings set2 = new ParserSettings(true, "en-US");
        ParserSettings set3 = new ParserSettings(false, "en-US");
        assert set1 != set2;
        assert set1.equals(set2);
        assert !set1.equals(set3);

        ParsedWordInText eq1 = new ParsedWordInText("сосиска",
                new HashSet<String>(Arrays.asList("сОсиска", "сосИска")), set1, 1, 2);
        ParsedWordInText eq2 = new ParsedWordInText("сосиска",
                new HashSet<String>(Arrays.asList("сосИска", "сОсиска")), set2, 1, 2);

        ParsedWordInText diff1 = new ParsedWordInText("не сосиска",
                new HashSet<String>(Arrays.asList("сОсиска", "сосИска")), set1, 1, 2);
        ParsedWordInText diff2 = new ParsedWordInText("сосиска",
                new HashSet<String>(Arrays.asList("сОсиска", "не сосИска")), set1, 1, 2);
        ParsedWordInText diff3 = new ParsedWordInText("сосиска",
                new HashSet<String>(Arrays.asList("сОсиска", "сосИска")), set3, 1, 2);
        ParsedWordInText diff4 = new ParsedWordInText("сосиска",
                new HashSet<String>(Arrays.asList("сОсиска", "сосИска")), set1, 2, 2);
        ParsedWordInText diff5 = new ParsedWordInText("сосиска",
                new HashSet<String>(Arrays.asList("сОсиска", "сосИска")), set1, 1, 1);

        assert eq1 != eq2;
        assertEquals(eq1, eq2);

        assertNotEquals(eq1, diff1);
        assertNotEquals(eq1, diff2);
        assertNotEquals(eq1, diff3);
        assertNotEquals(eq1, diff4);
        assertNotEquals(eq1, diff5);
    }

    @Test
    @DisplayName("Test constructor, equal() and hashCode() of TextParsingResult class")
    void testTextParsingResultClass() {
        ParserSettings set1 = new ParserSettings(true, "en-US");
        ParserSettings set2 = new ParserSettings(true, "en-US");
        ParserSettings set3 = new ParserSettings(false, "en-US");
        assert set1 != set2;
        assert set1.equals(set2);
        assert !set1.equals(set3);

        ParsedWordInText parsedWord1 = new ParsedWordInText("сосиска",
                new HashSet<String>(Arrays.asList("сОсиска", "сосИска")), set1, 1, 2);
        ParsedWordInText parsedWord2 = new ParsedWordInText("сосиска",
                new HashSet<String>(Arrays.asList("сОсиска", "сосИска")), set1, 1, 2);
        ParsedWordInText parsedWord3 = new ParsedWordInText("не сосиска",
                new HashSet<String>(Arrays.asList("сОсиска", "сосИска")), set1, 1, 2);
        assert parsedWord1 != parsedWord2;
        assert parsedWord1.equals(parsedWord2);
        assert !parsedWord1.equals(parsedWord3);

        TextParsingResult eq1 = new TextParsingResult("сосика",
                new ArrayList<ParsedWordInText>(Collections.singletonList(parsedWord1)), set1);
        TextParsingResult eq2 = new TextParsingResult("сосика",
                new ArrayList<ParsedWordInText>(Collections.singletonList(parsedWord2)), set2);

        TextParsingResult diff1 = new TextParsingResult("не сосика",
                new ArrayList<ParsedWordInText>(Collections.singletonList(parsedWord1)), set1);
        TextParsingResult diff2 = new TextParsingResult("сосика",
                new ArrayList<ParsedWordInText>(Collections.singletonList(parsedWord3)), set1);
        TextParsingResult diff3 = new TextParsingResult("сосика",
                new ArrayList<ParsedWordInText>(Collections.singletonList(parsedWord1)), set3);

        assert eq1 != eq2;
        assertEquals(eq1, eq2);

        assertNotEquals(eq1, diff1);
        assertNotEquals(eq1, diff2);
        assertNotEquals(eq1, diff3);


        TextParsingResult null1 = new TextParsingResult("сосика", new ArrayList<ParsedWordInText>(), set1);
        TextParsingResult null2 = new TextParsingResult("сосика", 0, new ArrayList<ParsedWordInText>(), set1);
        assertNull(null1.getWordsThatHaveHomoforms());
        assertNull(null2.getWordsThatHaveHomoforms());
    }
}
