package me.nikitaserba.rsw.rest.controllers;

import me.nikitaserba.rsw.rest.repsonses.AboutResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
