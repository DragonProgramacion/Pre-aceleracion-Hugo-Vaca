package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.entity.PersonajeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PersonajeMapper {

    @Autowired
    private PeliculaMapper peliculaMapper;

    public PersonajeEntity personajeDTO2Entity(PersonajeDTO dto){
        PersonajeEntity entity = new PersonajeEntity();
        entity.setImagen(dto.getImagen());
        entity.setNombre(dto.getNombre());
        entity.setEdad(dto.getEdad());
        entity.setPeso(dto.getPeso());
        entity.setHistoria(dto.getHistoria());
        return entity;
    }

    public PersonajeDTO personajeEntity2DTO(PersonajeEntity personajeEntity, boolean loadPeliculas){
        PersonajeDTO dto = new PersonajeDTO();
        dto.setId(personajeEntity.getId());
        dto.setNombre(personajeEntity.getNombre());
        dto.setImagen(personajeEntity.getImagen());
        dto.setEdad(personajeEntity.getEdad());
        dto.setPeso(personajeEntity.getPeso());
        dto.setHistoria(personajeEntity.getHistoria());

        if(loadPeliculas){
            List<PeliculaDTO> peliculasDTO = this.peliculaMapper.peliculaEntityList2DTOList(personajeEntity.getPeliculas(), false);
            dto.setPeliculas(peliculasDTO);
        }
        return dto;
    }

    public void personajeEntityRefreshValues(PersonajeEntity personajeEntity, PersonajeDTO personajeDTO){
        personajeEntity.setImagen(personajeDTO.getImagen());
        personajeEntity.setNombre(personajeDTO.getNombre());
        personajeEntity.setEdad(personajeDTO.getEdad());
        personajeEntity.setPeso(personajeDTO.getPeso());
        personajeEntity.setHistoria(personajeDTO.getHistoria());
    }

    public List<PersonajeDTO> personajeEntitySet2DTOList(Collection<PersonajeEntity> entities, boolean loadMovies) {
        List<PersonajeDTO> dtos = new ArrayList<>();
        for (PersonajeEntity entity : entities) {
            dtos.add(personajeEntity2DTO(entity, loadMovies));
        }
        return dtos;
    }

    public Set<PersonajeEntity> personajeDTOList2EntitySet(List<PersonajeDTO> dtos) {
        Set<PersonajeEntity> entities = new HashSet<>();
        for (PersonajeDTO dto : dtos) {
            entities.add(personajeDTO2Entity(dto));
        }
        return entities;
    }
    
    public PersonajeBasicDTO personajeEntity2BasicDTO(PersonajeEntity entity) {
        PersonajeBasicDTO dto = new PersonajeBasicDTO();
        dto.setId(entity.getId());
        dto.setImagen(entity.getImagen());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public List<PersonajeBasicDTO> personajeEntityList2BasicDTOList(List<PersonajeEntity> entities){
        List<PersonajeBasicDTO> dtos = new ArrayList<>();
        for(PersonajeEntity personaje : entities){
            dtos.add(this.personajeEntity2BasicDTO(personaje));
        }
        return dtos;
    }
}
