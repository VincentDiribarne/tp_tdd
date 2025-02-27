package com.vd.tp.validator;

import com.vd.tp.exception.isbn.FormatException;
import com.vd.tp.exception.isbn.LengthException;

public class ISBNValidator {
    public boolean validate(String isbn) {
        if (isbn.contains("-")) isbn = isbn.replace("-", "");

        if (isbn.length() != 13) throw new LengthException("ISBN must be 13 characters long");

        if (!isbn.matches("\\d+")) throw new FormatException("ISBN must contain only digits");

        int sum = 0;

        for (int i = 0; i < isbn.length(); i++) {
            char c = isbn.charAt(i);

            if (i % 2 == 0) {
                sum += Character.getNumericValue(c);
            } else {
                sum += Character.getNumericValue(c) * 3;
            }
        }

        return sum % 10 == 0;
    }
}
