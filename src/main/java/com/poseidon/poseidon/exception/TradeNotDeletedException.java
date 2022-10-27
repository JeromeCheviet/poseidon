package com.poseidon.poseidon.exception;

public class TradeNotDeletedException extends IllegalArgumentException {
    private String message;
    public TradeNotDeletedException() {

    }

    public TradeNotDeletedException(String msg) {
        super(msg);
        this.message = msg;
    }
}
