package me.nikitaserba.rsw.parser;

/**
 * Enum that stored the type of homograph parsed from dictionary.
 */
public enum HomographType {

    ONE_PART_OF_SPEECH,  // different homoforms of the word are the same part of speech
    DIFFERENT_PART_OF_SPEECH, // different homoforms of the word are different part of speech
    FORMS_OF_ONE_WORD, // different homoforms of the word are forms of the one particular word,
    // word may be understood incorrectly due to existence of homoforms ONLY if it may be a proper name (starts with capital letter)
    PROPER_NAME

}
