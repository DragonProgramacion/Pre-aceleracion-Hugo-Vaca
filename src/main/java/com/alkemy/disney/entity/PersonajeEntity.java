package com.alkemy.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "personaje")
@Getter @Setter
@SQLDelete(sql = "UPDATE personaje SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class PersonajeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String imagen;

    private String nombre;

    private Double peso;

    private Integer edad;

    private String historia;

    @ManyToMany(mappedBy = "personajes",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<PeliculaEntity> peliculas = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonajeEntity other = (PersonajeEntity) obj;
        return Objects.equals(id, other.id);
    }

}
