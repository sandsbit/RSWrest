package me.nikitaserba.rsw.parser;

import manifold.ext.rt.api.Jailbreak;
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

}
