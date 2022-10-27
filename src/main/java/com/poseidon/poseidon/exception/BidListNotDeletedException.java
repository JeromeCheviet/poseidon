package com.poseidon.poseidon.exception;

public class BidListNotDeletedException extends RuntimeException {
    private String message;

    public BidListNotDeletedException() {

    }

    public BidListNotDeletedException(String msg) {
        super(msg);
        this.message = msg;
    }
}
