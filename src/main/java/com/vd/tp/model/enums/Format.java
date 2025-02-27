package com.vd.tp.model.enums;

import lombok.Getter;

@Getter
public enum Format {
    PAPERBACK("Livre de poche"),
    HARDCOVER("Livre relié"),
    EBOOK("Livre numérique"),
    BD("Bande dessinée"),
    ROMAN("Roman");

    private final String name;

    Format(String name) {
        this.name = name;
    }
}
