package com.alkemy.disney.services;

import com.alkemy.disney.dto.GenreDTO;

import java.util.List;

public interface GenreService {

    GenreDTO save(GenreDTO dto);

    List<GenreDTO> getAll();
}
