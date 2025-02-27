package com.vd.tp.model;

import com.vd.tp.model.enums.Format;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private String isbn;
    private String title;
    private String author;
    private String editor;

    @Enumerated(EnumType.STRING)
    private Format format;

    private boolean available = true;
}