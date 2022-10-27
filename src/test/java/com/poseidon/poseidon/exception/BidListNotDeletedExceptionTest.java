package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BidListNotDeletedExceptionTest {

    @Test
    void testBidListNotDeletedException_withoutMessage() {
        assertThrows(BidListNotDeletedException.class, () -> {
            throw new BidListNotDeletedException();
        });
    }

    @Test
    void testBidListNotDeletedException_withMessage() {
        String expectedMessage = "an error";

        Throwable exception = assertThrows(BidListNotDeletedException.class, () -> {
            throw new BidListNotDeletedException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}
