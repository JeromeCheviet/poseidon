package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RuleNameNotDeletedExceptionTest {

    @Test
    void testRuleNameNotDeletedException_withoutMessage() {
        assertThrows(RuleNameNotDeletedException.class, () -> {
            throw new RuleNameNotDeletedException();
        });
    }

    @Test
    void testRuleNameNotDeletedException_withMessage() {
        String expectedMessage = "an error";

        Throwable exception = assertThrows(RuleNameNotDeletedException.class, () -> {
            throw new RuleNameNotDeletedException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}
