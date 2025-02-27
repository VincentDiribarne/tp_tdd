package com.vd.tp.validator;

public class ISBNValidator {
    public boolean validate(String isbn) {
        int sum = 0;

        if (isbn.contains("-")) {
            isbn = isbn.replace("-", "");
        }

        if (isbn.length() != 13) return false;


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
