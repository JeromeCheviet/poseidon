package com.poseidon.poseidon.exception;

public class RatingNotDeletedException extends IllegalArgumentException {

    private String message;

    public RatingNotDeletedException() {

    }

    public RatingNotDeletedException(String msg) {
        super(msg);
        this.message = msg;
    }
}
