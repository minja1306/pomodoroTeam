package eu.execom.pomodoroTeam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.jboss.logging.Logger;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger log = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {
        log.info("Entity not found!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerErrorException(Exception e, WebRequest request) {
        log.info("Internal server error!");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logException(Exception e) {
        log.info("Exception" + e + "occurred!");

    }
}

/*
 protected ResponseEntity<ExceptionMessage> errorResponse(Throwable throwable,
            HttpStatus status) {
        if (null != throwable) {
            log.error("error caught: " + throwable.getMessage(), throwable);
            return response(new ExceptionMessage(throwable), status);
        } else {
            log.error("unknown error caught in RESTController, {}", status);
            return response(null, status);
        }
    }
*/