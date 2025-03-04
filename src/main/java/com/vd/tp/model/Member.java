package com.vd.tp.model;

import com.vd.tp.model.enums.Civility;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends PersistentEntity {
    private String firstName;
    private String lastName;
    private String email;

    private String memberCode;

    private LocalDate birthDate;
    private Civility civility;

    @OneToMany
    private List<Reservation> reservations = new ArrayList<>();
}