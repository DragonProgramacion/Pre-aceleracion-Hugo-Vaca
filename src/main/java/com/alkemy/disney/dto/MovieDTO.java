package com.alkemy.disney.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import java.util.List;

@Getter @Setter
public class MovieDTO {
    private Long Id;
    private String image;
    private String title;
    private String creationDate;
    @Max(value = 5)
    private Integer qualification;
    private List<CharacterDTO> characters;
    private Long genreId;
}
