package com.vd.tp.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Format {
    PAPERBACK("Livre de poche"),
    HARDCOVER("Livre relié"),
    EBOOK("Livre numérique"),
    BD("Bande dessinée"),
    ROMAN("Roman");

    private final String name;
}
