package com.vd.tp.model;

import com.vd.tp.model.enums.Civility;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Person {
    private String memberCode;

    private LocalDate birthDate;
    private Civility civility;
}
