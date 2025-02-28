package com.vd.tp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

    private boolean closed;

    @ManyToOne
    private Book book;
}