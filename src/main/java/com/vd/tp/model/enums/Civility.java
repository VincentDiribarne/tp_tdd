package com.vd.tp.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Civility {
    MR("Monsieur", "M."),
    MRS("Madame", "Mme");

    private final String longFormulation;
    private final String shortFormulation;
}
