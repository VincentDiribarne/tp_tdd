package com.vd.tp.model;

import com.vd.tp.model.enums.Format;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends PersistentEntity {
    private String title;
    private String editor;
    private Format format;

    @ManyToOne
    private Person author;

    private boolean available;
}
