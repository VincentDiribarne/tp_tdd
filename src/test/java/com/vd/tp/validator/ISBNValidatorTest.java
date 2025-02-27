package com.vd.tp.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ISBNValidatorTest {
    @Test
    public void shouldIsbnValid() {
        ISBNValidator isbnValidator = new ISBNValidator();

        boolean result = isbnValidator.validate("978-1917067287");

        assertTrue(result);
    }
}
