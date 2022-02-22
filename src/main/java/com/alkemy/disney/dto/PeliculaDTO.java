package com.alkemy.disney.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import java.util.List;

@Getter @Setter
public class PeliculaDTO {
    private Long Id;
    private String imagen;
    private String titulo;
    private String fechaCreacion;
    @Max(value = 5)
    private Integer calificacion;
    private List<PersonajeDTO> personajes;
    private Long generoId;
}
