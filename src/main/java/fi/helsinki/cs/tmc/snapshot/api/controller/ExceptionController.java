package fi.helsinki.cs.tmc.snapshot.api.controller;

import fi.helsinki.cs.tmc.snapshot.api.exception.NotFoundException;
import fi.helsinki.cs.tmc.snapshot.api.model.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public final class ExceptionController {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(Exception.class)
    public Error handleException(final Exception exception) {

        LOG.error(exception.getMessage());

        return new Error("Something went wrong.");
    }

    @ExceptionHandler(NotFoundException.class)
    public Error handleNotFoundException() {

        return new Error("Not found.");
    }
}
