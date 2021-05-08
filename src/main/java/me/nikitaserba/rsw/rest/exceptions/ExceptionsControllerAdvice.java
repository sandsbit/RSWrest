package me.nikitaserba.rsw.rest.exceptions;

import com.google.gson.Gson;
import me.nikitaserba.rsw.parser.exceptions.InvalidSessionTokenException;
import me.nikitaserba.rsw.rest.repsonses.ErrorResponse;
import me.nikitaserba.rsw.rest.repsonses.ErrorResponseWithId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsControllerAdvice {

    @Autowired private Gson gson;

    @ExceptionHandler(IntervalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerErrorHandler(IntervalServerErrorException ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getId(), ex.getMessage());
    }

    @ExceptionHandler(InvalidSessionTokenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFoundHandler(InvalidSessionTokenException ex) {
        ErrorResponseWithId error = new ErrorResponseWithId(HttpStatus.NOT_FOUND.value(), "NO_SUCH_SESSION", ex.getMessage(), ex.getId());
        // why don't just return ErrorResponseWithId? because then Spring will replace json with standard 404 response and I don't know to fix it
        return gson.toJson(error);
    }

}
