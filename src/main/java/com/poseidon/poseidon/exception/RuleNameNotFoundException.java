package com.poseidon.poseidon.exception;

public class RuleNameNotFoundException extends IllegalArgumentException {
    private String message;

    public RuleNameNotFoundException() {

    }

    public RuleNameNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }

}
