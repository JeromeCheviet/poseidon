package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RatingNotDeletedExceptionTest {

    @Test
    void testRatingNotDeletedException_withoutMessage() {
        assertThrows(RatingNotDeletedException.class, () -> {
            throw new RatingNotDeletedException();
        });
    }

    @Test
    void testRatingNotDeletedException_withMessage() {
        String expectedMessage = "an error";

        Throwable exception = assertThrows(RatingNotDeletedException.class, () -> {
            throw new RatingNotDeletedException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}
