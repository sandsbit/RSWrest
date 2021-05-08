package me.nikitaserba.rsw.rest.controllers;

import me.nikitaserba.rsw.parser.HomographParser;
import me.nikitaserba.rsw.parser.ParserSettings;
import me.nikitaserba.rsw.parser.exceptions.InvalidSessionTokenException;
import me.nikitaserba.rsw.parser.Session;
import me.nikitaserba.rsw.rest.repsonses.SessionCreatedResponse;
import me.nikitaserba.rsw.rest.exceptions.IntervalServerErrorException;

import me.nikitaserba.rsw.rest.repsonses.SessionSettingsChangeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sessions")
@RestController
public class SessionsController {

    /**
     * Creates new session, new session id and its expire time are returns.
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> endSession(@PathVariable String id) throws InvalidSessionTokenException {
        HomographParser.endSession(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Change all settings of the session or reset them to default.
     *
     * ALL fields that will be null or are not stated in request body will be set to default.
     *
     * Returns all settings of session after changing.
     */
    @PutMapping(value = "/{id}", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public SessionSettingsChangeResponse changeAllSettings(
            @PathVariable String id,
            @RequestBody ParserSettings newSettings
    ) throws InvalidSessionTokenException {
        HomographParser.setSessionSettings(id, newSettings, HomographParser.SessionSettingsNullPolicies.SET_TO_DEFAULT);
        return new SessionSettingsChangeResponse(HttpStatus.OK.value(), id, HomographParser.getSessionSettings(id));
    }

    /**
     * Change all settings of the session or reset them to default.
     *
     * ALL fields that will be null or are not stated in request body will be set to default.
     *
     * Returns all settings of session after changing.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public SessionSettingsChangeResponse changeSomeSettings(
            @PathVariable String id,
            @RequestBody ParserSettings newSettings
    ) throws InvalidSessionTokenException {
        HomographParser.setSessionSettings(id, newSettings, HomographParser.SessionSettingsNullPolicies.DONT_CHANGE);
        return new SessionSettingsChangeResponse(HttpStatus.OK.value(), id, HomographParser.getSessionSettings(id));
    }
}
