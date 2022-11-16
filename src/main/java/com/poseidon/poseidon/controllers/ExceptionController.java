package com.poseidon.poseidon.controllers;

import com.poseidon.poseidon.exception.DataNotDeletedException;
import com.poseidon.poseidon.exception.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LogManager.getLogger(ExceptionController.class);

    @ExceptionHandler(DataNotDeletedException.class)
    public ModelAndView dataNotDeletedExceptionHandler(DataNotDeletedException exception) {
        logger.error(exception.getMessage());

        ModelAndView model = new ModelAndView();
        model.addObject("errorMsg", exception.getMessage());
        model.setStatus(HttpStatus.BAD_REQUEST);
        model.setViewName("400");
        return model;
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    public ModelAndView dataNotFoundHandler(DataNotFoundException exception) {
        logger.error(exception.getMessage());

        ModelAndView model = new ModelAndView();
        model.addObject("errorMsg", exception.getMessage());
        model.setStatus(HttpStatus.NOT_FOUND);
        model.setViewName("404");
        return model;
    }

}
