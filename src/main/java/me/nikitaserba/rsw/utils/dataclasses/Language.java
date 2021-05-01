package me.nikitaserba.rsw.utils.dataclasses;

import java.util.Objects;

/**
 * This data class represents language supported by service.
 *
 * It is designed to be easily deserialized from json. Compare
 * class version with api version stated in JSON file.
 *
 * Language name is NOT used in comparison or generating hash of
 * object. Language classes with the same language code are considered
 * EQUAL.
 *
 * @version 0
 */
public final class Language {

    private final String LanguageName;
    private final String LanguageCode;

    // --- END FIELDS ---

    public Language(String languageName, String languageCode) {
        LanguageName = languageName;
        LanguageCode = languageCode;
    }

    public String getLanguageName() {
        return LanguageName;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name: '" + LanguageName + '\'' +
                ", code: '" + LanguageCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return LanguageCode.equals(language.LanguageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(LanguageCode);
    }
}
