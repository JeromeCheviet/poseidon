package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BidListNotFoundExceptionTest {

    @Test
    void testDataNotFoundException_withoutMessage() {
        assertThrows(BidListNotFoundException.class, () -> {
            throw new BidListNotFoundException();
        });
    }

    @Test
    void testDataNotFoundException_withMessage() {
        String expectedMessage = "X not found";

        Throwable exception = assertThrows(BidListNotFoundException.class, () -> {
            throw new BidListNotFoundException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}
