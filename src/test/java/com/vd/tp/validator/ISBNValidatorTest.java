package com.vd.tp.validator;

import com.vd.tp.exception.isbn.FormatException;
import com.vd.tp.exception.isbn.LengthException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void shouldIsbnInvalidTooShort() {
        ISBNValidator isbnValidator = new ISBNValidator();

        assertThrows(LengthException.class, () -> isbnValidator.validate("9781917068"));
    }

    @Test
    public void shouldIsbnInvalidTooLong() {
        ISBNValidator isbnValidator = new ISBNValidator();

        assertThrows(LengthException.class, () -> isbnValidator.validate("97819170672888"));
    }

    @Test
    public void shouldIsbnInvalidWithLetters() {
        ISBNValidator isbnValidator = new ISBNValidator();

        assertThrows(FormatException.class, () -> isbnValidator.validate("978191706728a"));
    }
}