package com.poseidon.poseidon.exception;

public class RuleNameNotDeletedException extends IllegalArgumentException {
    private String message;

    public RuleNameNotDeletedException() {

    }

    public RuleNameNotDeletedException(String msg) {
        super(msg);
        this.message = msg;
    }
}
