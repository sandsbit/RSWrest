package me.nikitaserba.rsw.parser;

public enum HomographType {

    ONE_PART_OF_SPEECH,  // different homoforms of the word are the same part of speech
    DIFFERENT_PART_OF_SPEECH, // different homoforms of the word are different part of speech
    FORMS_OF_ONE_WORD, // different homoforms of the word are forms of the one particular word,
    PROPER_NAME // some homoforms of the world are proper names, while others are not

}
