package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurvePointNotDeletedExceptionTest {

    @Test
    void testCurvePointNotDeletedException_withoutMessage() {
        assertThrows(CurvePointNotDeletedException.class, () -> {
            throw new CurvePointNotDeletedException();
        });
    }

    @Test
    void testCurvePointNotDeletedException_withMessage() {
        String expectedMessage = "an error";

        Throwable exception = assertThrows(CurvePointNotDeletedException.class, () -> {
            throw new CurvePointNotDeletedException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

}