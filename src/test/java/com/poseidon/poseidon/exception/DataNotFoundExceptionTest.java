package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataNotFoundExceptionTest {

    @Test
    void testDataNotFoundException() {
        String expectedMessage = "An error";

        Throwable exception = assertThrows(DataNotFoundException.class, () -> {
            throw new DataNotFoundException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

}