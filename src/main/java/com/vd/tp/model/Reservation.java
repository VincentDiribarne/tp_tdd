package com.vd.tp.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends PersistentEntity {
    private LocalDate reservationDate;
    private LocalDate dueDate;
}