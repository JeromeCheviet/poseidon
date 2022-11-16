package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataNotDeletedExceptionTest {

    @Test
    void testDataNotDeletedException() {
        String expectedMessage = "an error";

        Throwable exception = assertThrows(DataNotDeletedException.class, () -> {
            throw new DataNotDeletedException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

}