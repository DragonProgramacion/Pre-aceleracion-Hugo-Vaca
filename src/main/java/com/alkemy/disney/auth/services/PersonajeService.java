package com.alkemy.disney.auth.services;


import com.alkemy.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.entity.PersonajeEntity;

import java.util.List;
import java.util.Set;

public interface PersonajeService {

    PersonajeDTO save(PersonajeDTO dto, Long idPelicula);

    void delete(Long id);

    List<PersonajeBasicDTO> getByFilters(String nombre, Integer edad, Set<Long> peliculas, String order);

    PersonajeDTO update(Long id, PersonajeDTO personaje);

    PersonajeDTO getDetailsById(Long id);

    PersonajeEntity getEntityById(Long id);
}
