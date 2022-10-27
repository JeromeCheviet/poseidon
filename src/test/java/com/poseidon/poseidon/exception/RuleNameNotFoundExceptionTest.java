package com.poseidon.poseidon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RuleNameNotFoundExceptionTest {

    @Test
    void testRuleNameNotFoundException_withoutMessage() {
        assertThrows(RuleNameNotFoundException.class, () -> {
            throw new RuleNameNotFoundException();
        });
    }

    @Test
    void testRuleNameNotFoundException_withMessage() {
        String expectedMessage = "an error";

        Throwable exception = assertThrows(RuleNameNotFoundException.class, () -> {
            throw new RuleNameNotFoundException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

}
