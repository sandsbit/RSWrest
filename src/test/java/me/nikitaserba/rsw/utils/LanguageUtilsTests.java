package me.nikitaserba.rsw.utils;


import me.nikitaserba.rsw.utils.dataclasses.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageUtilsTests {

    private LanguageUtils utils;

    @BeforeEach
    public void setUp() {
        utils = LanguageUtils.getInstance();
    }

    @Test
    @DisplayName("Test that language utils return number of languages properly")
    public void testNumberOfLanguages() {
        assertEquals(3, utils.numberOfLanguages());
    }

    @Test
    @DisplayName("Test that language is properly returned by code")
    public void testGetLanguageByCode() {
        Language usEnglish = utils.getLanguageByCode("en-US");
        assertNotNull(usEnglish);
        assertEquals("en-US", usEnglish.getLanguageCode());
        assertEquals("English (U.S.)", usEnglish.getLanguageName());
    }

    @Test
    @DisplayName("Test that language utils can handle asking for nonexistent language")
    public void testGetInvalidLanguageByCode() {
        Language ukrainian = utils.getLanguageByCode("uk-UA");
        assertNull(ukrainian);
    }

    @Test
    @DisplayName("Test that language utils properly return unmodifiable set of codes")
    public void testGetAllLanguagesCodes() {
        Set<String> languageCodesExpected = new HashSet<>(Arrays.asList("ru-RU", "en-EN", "en-US"));
        Set<String> languageCodes = utils.getAllLanguageCodes();
        assertEquals(languageCodesExpected, languageCodes);
        assertThrows(RuntimeException.class, () -> {
            languageCodes.add("test");
        });
    }

}
