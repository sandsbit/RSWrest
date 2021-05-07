package me.nikitaserba.rsw.rest.controllers;

import me.nikitaserba.rsw.parser.HomographParser;
import me.nikitaserba.rsw.parser.InvalidSessionTokenException;
import me.nikitaserba.rsw.parser.Session;
import me.nikitaserba.rsw.rest.repsonses.SessionCreatedResponse;
import me.nikitaserba.rsw.rest.exceptions.IntervalServerErrorException;

import me.nikitaserba.rsw.rest.repsonses.SessionDeletedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sessions")
@RestController
public class SessionsController {

    /**
     * Creates new session, new session id and its expire time are returnes.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionCreatedResponse createNewSession() {
        String id = HomographParser.startSession();
        if (id == null)
            throw new IntervalServerErrorException("COULD_NOT_BEGIN_SESSION", "Unexpected error while creating session.");

        return new SessionCreatedResponse(HttpStatus.CREATED.value(), id, Session.SESSION_EXPIRE.toSeconds());
    }

    /**
     * Deletes session by its id.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SessionDeletedResponse endSession(@PathVariable String id) throws InvalidSessionTokenException {
        HomographParser.endSession(id);
        return new SessionDeletedResponse(HttpStatus.OK.value(), id);
    }
}
