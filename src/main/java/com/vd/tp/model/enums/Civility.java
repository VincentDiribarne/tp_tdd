package com.vd.tp.model.enums;

import lombok.Getter;

@Getter
public enum Civility {
    MR("Monsieur", "M."),
    MRS("Madame", "Mme");

    private final String longFormulation;
    private final String shortFormulation;

    Civility(String longFormulation, String shortFormulation) {
        this.longFormulation = longFormulation;
        this.shortFormulation = shortFormulation;
    }
}
