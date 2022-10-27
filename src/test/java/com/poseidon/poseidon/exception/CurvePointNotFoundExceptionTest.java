package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurvePointNotFoundExceptionTest {

    @Test
    void testCurvePointNotFoundException_withoutMessage() {
        assertThrows(CurvePointNotFoundException.class, () -> {
            throw new CurvePointNotFoundException();
        });
    }

    @Test
    void testCurvePointNotFoundException_withMessage() {
        String expectedMessage = "an error";

        Throwable exception = assertThrows(CurvePointNotFoundException.class, () -> {
            throw new CurvePointNotFoundException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}