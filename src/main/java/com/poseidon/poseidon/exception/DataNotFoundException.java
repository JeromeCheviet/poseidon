package com.poseidon.poseidon.exception;

/**
 * Class which create a DataNotFoundException
 */
public class DataNotFoundException extends RuntimeException {

    /**
     * Method use to construct an exception DataNotFoundException. An error message must be present to create
     * this exception
     *
     * @param message String whose contain error message to display.
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
