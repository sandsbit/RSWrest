package me.nikitaserba.rsw.rest.controllers;

import me.nikitaserba.rsw.parser.HomographParser;
import me.nikitaserba.rsw.parser.Session;
import me.nikitaserba.rsw.rest.repsonses.SessionCreatedResponse;
import me.nikitaserba.rsw.rest.exceptions.IntervalServerErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sessions")
@RestController
public class SessionsController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionCreatedResponse createNewSession() {
        String id = HomographParser.startSession();
        if (id == null)
            throw new IntervalServerErrorException("COULD_NOT_BEGIN_SESSION", "Unexpected error while creating session.");

        return new SessionCreatedResponse(HttpStatus.CREATED.value(), id, Session.SESSION_EXPIRE.toSeconds());
    }

}
