package me.nikitaserba.rsw.utils.dataclasses;

import java.util.Arrays;

/**
 * This data class represents list of languages
 * supported by service.
 *
 * It is designed to be easily deserialized from json. Compare
 * class version with api version stated in JSON file.
 *
 * @version 0
 */
public final class LanguageList {

    /**
     * This API version should be changed in every version (including beta, e.g.)
     * where this class is changed.
     *
     * The json file that is deserialized SHOULD have the same API. Parsing json
     * file with not the same API value MAY NOT cause Exception or any other error,
     * but WILL PROBABLY cause any other unexpected behavior.
     */
    public static final int CLASS_API_VERSION = 0;

    // It is NOT class api version, it is a api version read
    // from file.
    private final int apiVersion;

    private final Language[] languages;

    // --- END FIELDS ---

    public LanguageList(int apiVersion, Language[] languages) {
        this.apiVersion = apiVersion;
        this.languages = languages;
        // TODO: Warning if api versions don't match
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public Language[] getLanguages() {
        return languages;
    }

    @Override
    public String toString() {
        return "LanguageList{" +
                "API version: " + apiVersion +
                ", languages: " + Arrays.toString(languages) +
                '}';
    }
}
