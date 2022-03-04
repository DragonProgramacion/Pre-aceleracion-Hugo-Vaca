package com.alkemy.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "movie")
@Getter @Setter
@SQLDelete(sql = "UPDATE movie SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String image;

    private String title;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    private Integer qualification;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private GenreEntity genreEntity;

    @Column(name = "genre_id",nullable = false)
    private Long genreId;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "character_movie", joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private Set<CharacterEntity> characters = new HashSet<>();

    private Boolean deleted = Boolean.FALSE;

    public void addCharacter(CharacterEntity character) {
        this.characters.add(character);
    }

    public void removeCharacter(CharacterEntity character) {
        this.characters.remove(character);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MovieEntity other = (MovieEntity) obj;
        return other.id == this.id;
    }




}
