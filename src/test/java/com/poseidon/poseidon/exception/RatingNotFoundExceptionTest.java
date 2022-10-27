package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RatingNotFoundExceptionTest {

    @Test
    void testRatingNotFoundException_withoutMessage() {
        assertThrows(RatingNotFoundException.class, () -> {
            throw new RatingNotFoundException();
        });
    }

    @Test
    void testRatingNotFoundException_withMessage() {
        String expectedMessage = "an error";

        Throwable exception = assertThrows(RatingNotFoundException.class, () -> {
            throw new RatingNotFoundException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}
