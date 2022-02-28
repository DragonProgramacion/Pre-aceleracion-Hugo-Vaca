package com.alkemy.disney.services;


import com.alkemy.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.entity.PeliculaEntity;

import java.util.List;
import java.util.Set;

public interface PeliculaService {

    PeliculaDTO save(PeliculaDTO dto);

    void delete(Long id);

    PeliculaDTO update(Long id, PeliculaDTO peliculaDTO);

    void addPersonaje(Long id, Long idPersonaje);

    void removePersonaje(Long id, Long idPersonaje);

    List<PeliculaBasicDTO> getByFilters(String nombre, Set<Long> generos, String order);

    PeliculaDTO getDetailsById(Long id);

    PeliculaEntity getEntityById(Long id);
}
