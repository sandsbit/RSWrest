package me.nikitaserba.rsw.rest.controllers;

import me.nikitaserba.rsw.rest.repsonses.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cake")
@RestController
public class SuperSecretController {

    @GetMapping
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public MessageResponse getCake() {
        return new MessageResponse(418, "Sorry, cake is a lie.");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public MessageResponse postCake() {
        return new MessageResponse(418, "Thank you for assuming the party escort submission position.");
    }

}
