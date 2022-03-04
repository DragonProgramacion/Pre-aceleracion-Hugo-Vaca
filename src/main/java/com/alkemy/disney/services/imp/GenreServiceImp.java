package com.alkemy.disney.services.imp;

import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.mapper.GenreMapper;
import com.alkemy.disney.repository.GenreRepository;
import com.alkemy.disney.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImp implements GenreService {

    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private GenreRepository genreRepository;

    public GenreDTO save(GenreDTO dto) {
        GenreEntity entity = genreMapper.genreDTO2Entity(dto);
        GenreEntity generoSaved = genreRepository.save(entity);
        GenreDTO result = genreMapper.genreEntity2DTO(generoSaved);
        return result;

    }

    public List<GenreDTO> getAll() {
        List<GenreEntity> entities = this.genreRepository.findAll();
        List<GenreDTO> result = this.genreMapper.genreEntityList2DTOList(entities);
        return result;
    }

}
