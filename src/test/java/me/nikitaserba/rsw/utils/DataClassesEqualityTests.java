package me.nikitaserba.rsw.utils;

import me.nikitaserba.rsw.utils.dataclasses.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DataClassesEqualityTests {
    @Test
    @DisplayName("Test equal() and hashCode() of Language class")
    void testLanguageClass() {
        Language eq1 = new Language("Russian", "ru-RU");
        Language eq2 = new Language("Russian (Russia)", "ru-RU");

        Language diff1 = new Language("Russian", "ru-ES");

        assert eq1 != eq2;
        assertEquals(eq1, eq2);

        assertNotEquals(eq1, diff1);
    }
}
