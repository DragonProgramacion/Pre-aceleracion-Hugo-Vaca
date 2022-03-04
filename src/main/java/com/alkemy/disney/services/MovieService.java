package com.alkemy.disney.services;


import com.alkemy.disney.dto.MovieBasicDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.entity.MovieEntity;

import java.util.List;
import java.util.Set;

public interface MovieService {

    MovieDTO save(MovieDTO dto);

    void delete(Long id);

    MovieDTO update(Long id, MovieDTO movieDTO);

    void addCharacter(Long id, Long idCharacter);

    void removeCharacter(Long id, Long idCharacter);

    List<MovieBasicDTO> getByFilters(String name, Set<Long> genres, String order);

    MovieDTO getDetailsById(Long id);

    MovieEntity getEntityById(Long id);
}
