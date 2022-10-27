package com.poseidon.poseidon.exception;

public class CurvePointNotFoundException extends IllegalArgumentException {

    private static String message;

    public CurvePointNotFoundException() {

    }

    public CurvePointNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
