package com.alkemy.disney.dto;


import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class CharacterDTO {
    private Long id;
    private String image;
    private String name;
    private Integer age;
    private Double weight;
    private String story;
    private List<MovieDTO> movies;

}
