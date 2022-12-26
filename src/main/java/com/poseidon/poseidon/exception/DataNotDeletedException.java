package com.poseidon.poseidon.exception;

/**
 * Class which create a DataNotDeletedException
 */
public class DataNotDeletedException extends RuntimeException {

    /**
     * Method use to construct an exception DataNotDeletedException. An error message must be present to create
     * this exception
     *
     * @param message String whose contain error message to display.
     */
    public DataNotDeletedException(String message) {
        super(message);
    }
}
