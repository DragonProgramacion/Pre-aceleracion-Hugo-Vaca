package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.entity.PeliculaEntity;
import com.alkemy.disney.entity.PersonajeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class PeliculaMapper {

    @Autowired
    private PersonajeMapper personajeMapper;

    public PeliculaEntity peliculaDTO2Entity(PeliculaDTO dto){
        PeliculaEntity entity = new PeliculaEntity();
        entity.setImagen(dto.getImagen());
        entity.setTitulo(dto.getTitulo());
        entity.setFechaCreacion(this.string2LocalDate(dto.getFechaCreacion()));
        entity.setCalificacion(dto.getCalificacion());
        entity.setGeneroId(dto.getGeneroId());

        Set<PersonajeEntity> personajes = personajeMapper.personajeDTOList2EntitySet(dto.getPersonajes());
        entity.setPersonajes(personajes);
        return entity;
    }

    public PeliculaDTO peliculaEntity2DTO(PeliculaEntity peliculaEntity, boolean loadPersonajes){
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(peliculaEntity.getId());
        dto.setImagen(peliculaEntity.getImagen());
        dto.setTitulo(peliculaEntity.getTitulo());
        dto.setFechaCreacion(peliculaEntity.getFechaCreacion().toString());
        dto.setCalificacion(peliculaEntity.getCalificacion());
        dto.setGeneroId(peliculaEntity.getGeneroId());
        if(loadPersonajes){
            List<PersonajeDTO> personajesDTOS = personajeMapper.personajeEntitySet2DTOList(peliculaEntity.getPersonajes(), false);
            dto.setPersonajes(personajesDTOS);
        }
        return dto;
    }

    private LocalDate string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }

    public void peliculaEntityRefreshValues(PeliculaEntity peliculaEntity, PeliculaDTO peliculaDTO){
        peliculaEntity.setImagen(peliculaDTO.getImagen());
        peliculaEntity.setTitulo(peliculaDTO.getTitulo());
        peliculaEntity.setFechaCreacion(this.string2LocalDate(peliculaDTO.getFechaCreacion()));
        peliculaEntity.setCalificacion(peliculaDTO.getCalificacion());
        peliculaEntity.setGeneroId(peliculaDTO.getGeneroId());

        Set<PersonajeEntity> personajes = personajeMapper.personajeDTOList2EntitySet(peliculaDTO.getPersonajes());
        for (PersonajeEntity personajeEntity: personajes) {
            peliculaEntity.getPersonajes().add(personajeEntity);
        }
    }

    public List<PeliculaDTO> peliculaEntityList2DTOList(List<PeliculaEntity> entities, boolean loadCharacters) {
        List<PeliculaDTO> dtos = new ArrayList<>();
        for (PeliculaEntity entity : entities) {
            dtos.add(peliculaEntity2DTO(entity, loadCharacters));
        }
        return dtos;
    }

    public PeliculaBasicDTO peliculaEntity2BasicDTO(PeliculaEntity entity){
        PeliculaBasicDTO dto = new PeliculaBasicDTO();
        dto.setImagen(entity.getImagen());
        dto.setTitulo(entity.getTitulo());
        LocalDate fecha = entity.getFechaCreacion();
        String formatDate = fecha.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        dto.setFechaCreacion(formatDate);
        return dto;
    }

    public  List<PeliculaBasicDTO> peliculaEntityList2BasicDTOList(List<PeliculaEntity> entities) {
        List<PeliculaBasicDTO> dtos = new ArrayList<>();
        for (PeliculaEntity pelicula: entities) {
            dtos.add(this.peliculaEntity2BasicDTO(pelicula));
        }
        return dtos;
    }


}
