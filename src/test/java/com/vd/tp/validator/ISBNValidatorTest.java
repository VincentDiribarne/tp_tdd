package com.vd.tp.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ISBNValidatorTest {
    @Test
    public void shouldIsbnValid() {
        ISBNValidator isbnValidator = new ISBNValidator();

        boolean result = isbnValidator.validate("978-1917067287");

        assertTrue(result);
    }

    @Test
    public void shouldIsbnValidWithoutDash() {
        ISBNValidator isbnValidator = new ISBNValidator();

        boolean result = isbnValidator.validate("978-1917067287");

        assertTrue(result);
    }

    @Test
    public void shouldIsbnInvalid() {
        ISBNValidator isbnValidator = new ISBNValidator();

        boolean result = isbnValidator.validate("9781917067288");

        assertFalse(result);
    }

    @Test
    public void shouldIsbnInvalidWithoutDash() {
        ISBNValidator isbnValidator = new ISBNValidator();

        boolean result = isbnValidator.validate("9781917067288");

        assertFalse(result);
    }
}