package com.poseidon.poseidon.exception;

public class TradeNotFoundException extends IllegalArgumentException {
    private String message;

    public TradeNotFoundException() {

    }

    public TradeNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
