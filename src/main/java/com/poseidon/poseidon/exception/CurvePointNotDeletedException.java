package com.poseidon.poseidon.exception;

public class CurvePointNotDeletedException extends IllegalArgumentException {
    private static String message;

    public CurvePointNotDeletedException() {

    }

    public CurvePointNotDeletedException(String msg) {
        super(msg);
        this.message = msg;
    }
}
