package com.vd.tp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class PersistentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
