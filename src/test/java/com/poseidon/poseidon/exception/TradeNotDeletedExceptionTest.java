package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TradeNotDeletedExceptionTest {
    @Test
    void TradeNotDeletedException_withoutMessage() {
        assertThrows(TradeNotDeletedException.class, () -> {
            throw new TradeNotDeletedException();
        });
    }

    @Test
    void TradeNotDeletedException_withMessage() {
        String expectedMessage = "an error";

        Throwable exception = assertThrows(TradeNotDeletedException.class, () -> {
            throw new TradeNotDeletedException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}
