package com.vd.tp.model;

import com.vd.tp.model.enums.Format;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person extends PersistentEntity {
    private String firstName;
    private String lastName;
}
