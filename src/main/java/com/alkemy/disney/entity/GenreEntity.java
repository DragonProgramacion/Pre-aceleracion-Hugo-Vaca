package com.alkemy.disney.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "genre")
@Getter @Setter
@RequiredArgsConstructor
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String image;

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final GenreEntity other = (GenreEntity) obj;
        return other.id == this.id;
    }

}
