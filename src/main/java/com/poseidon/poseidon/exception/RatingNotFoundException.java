package com.poseidon.poseidon.exception;

public class RatingNotFoundException extends IllegalArgumentException {

    private static String message;

    public RatingNotFoundException() {

    }

    public RatingNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
