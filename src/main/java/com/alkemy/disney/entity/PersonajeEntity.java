package com.alkemy.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public void addPelicula(PeliculaEntity pelicula){
        peliculas.add(pelicula);
    }

    public void removePelicula(PeliculaEntity pelicula){
        peliculas.remove(pelicula);
    }

}
