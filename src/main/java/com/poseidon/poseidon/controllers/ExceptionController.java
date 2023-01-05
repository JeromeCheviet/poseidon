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

/**
 * Class to manage personal exceptions handler
 */
@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LogManager.getLogger(ExceptionController.class);

    /**
     * Method to populate the Model and view to send when data have not been deleted
     *
     * @param exception DataNotDeletedException
     * @return ModelAndView object to redirect to 400 page with error message generate by exception and http status 400
     */
    @ExceptionHandler(DataNotDeletedException.class)
    public ModelAndView dataNotDeletedExceptionHandler(DataNotDeletedException exception) {
        logger.error(exception.getMessage());

        ModelAndView model = new ModelAndView();
        model.addObject("errorMsg", exception.getMessage());
        model.setStatus(HttpStatus.BAD_REQUEST);
        model.setViewName("400");
        return model;
    }

    /**
     * Method to populate the Model and view to send when data have not found
     *
     * @param exception DataNotFoundException
     * @return ModelAndView object to redirect to 404 page with error message generate by exception and http status 404
     */
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
