package me.nikitaserba.rsw.rest.controllers;

import me.nikitaserba.rsw.rest.repsonses.AboutResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/about")
@RestController
public final class AboutController {

    /**
     * Returns general information about service.
     */
    @GetMapping
    public AboutResponse about() {
        return new AboutResponse();
    }

}
