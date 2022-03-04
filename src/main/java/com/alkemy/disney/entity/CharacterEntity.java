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
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String image;

    private String name;

    private Double weight;

    private Integer age;

    private String story;

    @ManyToMany(mappedBy = "characters",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<MovieEntity> movies = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CharacterEntity other = (CharacterEntity) obj;
        return Objects.equals(id, other.id);
    }

}
