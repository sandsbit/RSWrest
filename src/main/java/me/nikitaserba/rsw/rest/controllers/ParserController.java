package me.nikitaserba.rsw.rest.controllers;

import me.nikitaserba.rsw.parser.HomographParser;
import me.nikitaserba.rsw.parser.ParserSettings;
import me.nikitaserba.rsw.parser.exceptions.InvalidSessionTokenException;
import me.nikitaserba.rsw.parser.repsonses.ParsingResult;
import me.nikitaserba.rsw.rest.exceptions.BadlyFormedRequestException;
import me.nikitaserba.rsw.rest.requests.ParsingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parser")
public class ParserController {

    @PostMapping
    public ParsingResult parse(@RequestBody ParsingRequest request) throws InvalidSessionTokenException {
        boolean parsingText = request.getText() != null;
        boolean parsingWord = request.getWord() != null;
        ParserSettings settings = request.getSettings();
        if (parsingText && parsingWord)
            throw new BadlyFormedRequestException("Cannot parse word and text at the same time");
        if (!parsingText && !parsingWord)
            throw new BadlyFormedRequestException("No data to parse was sent");

        String id = request.getSessionId();
        if (id != null && settings != null)
            throw new BadlyFormedRequestException("Settings were passed in session request. Set settings of session" +
                                                  "instead.");

        if (id == null) {
            if (parsingWord)
                return HomographParser.parseWord(request.getWord(), settings);
            else
                return HomographParser.parseText(request.getText(), settings);
        } else {
            if (parsingWord)
                return HomographParser.s_parseWord(id, request.getWord());
            else
                return HomographParser.s_parseText(id, request.getText());
        }
    }

}
