package me.nikitaserba.rsw.utils;

import com.google.gson.Gson;
import me.nikitaserba.rsw.utils.dataclasses.Language;
import me.nikitaserba.rsw.utils.dataclasses.LanguageList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Collections;
import java.util.Set;

/**
 * This class handles all stuff that is connected
 * with supporting different languages.
 */
public final class LanguageUtils {

    /**
     * It is the file that is used by default as the list of supported languages.
     * It is considered to be put in project resources.
     *
     * P.S. Don't remove slash at the beginning.
     */
    private static final String RESOURCE_JSON_LANGUAGES_FILENAME = "/languages/languages.json";

    private LanguageList languages;

    private LanguageUtils() {}

    /**
     * Creates LanguageUtils class instance from standart languages list json file in resources.
     * See `LanguageUtils.RESOURCE_JSON_LANGUAGES_FILENAME` for more info.
     *
     * @return instance of LanguageUtils with data from resources json file.
     * @throws UncheckedIOException if there were errors while reading files.
     */
    public static LanguageUtils getInstance() {
        try {
            return getFromResources(RESOURCE_JSON_LANGUAGES_FILENAME);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Loads json file from resources and create LanguageUtils class instance from its data.
     *
     * @param file - file to read from.
     * @return instance of LanguageUtils class with data from given json.
     * @throws IOException if there were errors while loading file (most likely file does not exist).
     */
    public static LanguageUtils getFromResources(String file) throws IOException {
        InputStream inputStream = LanguageUtils.class.getResourceAsStream(file);

        if (inputStream == null)
            throw new IOException("Could not open file in resources: " + file);

        return getFromInputStream(inputStream);
    }

    /**
     * Loads json file and create LanguageUtils class instance from its data.
     *
     * @param file - file to read from.
     * @return instance of LanguageUtils class with data from given json.
     */
    public static LanguageUtils getFromFile(String file) throws FileNotFoundException {
        return getFromInputStream(new FileInputStream(file));
    }

    /**
     * Loads json from input stream and creates LanguageUtils class instance.
     *
     * @param ins - input stream to read from.
     * @return instance of LanguageUtils class with data from given json.
     */
    public static LanguageUtils getFromInputStream(InputStream ins) {
        return new Gson().fromJson(new InputStreamReader(ins, StandardCharsets.UTF_8), LanguageUtils.class);
    }

    /**
     * Return language by code.
     *
     * @param code - code to search with.
     * @return Language instance representing language with given code or `null` if nothing found.
     */
    public Language getLanguageByCode(String code) {
        for (Language lang : languages.getLanguages()) {
            if (code.equals(lang.getLanguageCode()))
                return lang;
        }
        return null;
    }

    /**
     * Get all languages.
     *
     * @return array of Language objects representing all supported languages.
     */
    public Language[] getAllLanguages() {
        return languages.getLanguages();
    }

    /**
     * Return the number of stored languages.
     *
     * @return the number of stored languages.
     */
    public int numberOfLanguages() {
        return languages.getLanguages().length;
    }


    private Set<String> allLanguagesCodesCache = null;

    /**
     * Returns set of all language codes.
     *
     * @return unmodifiable set of codes of all languages.
     */
    public Set<String> getAllLanguageCodes() {
        if (allLanguagesCodesCache == null) {
            Set<String> languageCodes = new HashSet<>(numberOfLanguages());
            for (Language lang : languages.getLanguages())
                languageCodes.add(lang.getLanguageCode());

            allLanguagesCodesCache = Collections.unmodifiableSet(languageCodes);
        }
        return allLanguagesCodesCache;
    }
}
