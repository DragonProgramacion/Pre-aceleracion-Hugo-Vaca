package com.alkemy.disney.services;


import com.alkemy.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.entity.CharacterEntity;

import java.util.List;
import java.util.Set;

public interface CharacterService {

    CharacterDTO save(CharacterDTO dto, Long idMovie);

    void delete(Long id);

    List<CharacterBasicDTO> getByFilters(String name, Integer age, Set<Long> movies, String order);

    CharacterDTO update(Long id, CharacterDTO character);

    CharacterDTO getDetailsById(Long id);

    CharacterEntity getEntityById(Long id);
}
