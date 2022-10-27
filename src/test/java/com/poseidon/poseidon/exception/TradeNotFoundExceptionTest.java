package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TradeNotFoundExceptionTest {
    @Test
    void testTradeNotFoundException_withoutMessage() {
        assertThrows(TradeNotFoundException.class, () -> {
            throw new TradeNotFoundException();
        });
    }

    @Test
    void testTradeNotFoundException_withMessage() {
        String expectedMessage = "an error";

        Throwable exception = assertThrows(TradeNotFoundException.class, () -> {
            throw new TradeNotFoundException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}
