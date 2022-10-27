package com.poseidon.poseidon.exception;

public class BidListNotFoundException extends IllegalArgumentException {
    private static String message;

    public BidListNotFoundException() {

    }

    public BidListNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
