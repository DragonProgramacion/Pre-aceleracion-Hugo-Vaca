package com.alkemy.disney.mapper;


import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.entity.GenreEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenreMapper {

    public GenreEntity genreDTO2Entity(GenreDTO dto) {
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName(dto.getName());
        genreEntity.setImage(dto.getImage());
        return genreEntity;

    }

    public GenreDTO genreEntity2DTO(GenreEntity entity) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(entity.getId());
        genreDTO.setName(entity.getName());
        genreDTO.setImage(entity.getImage());
        return genreDTO;
    }

    public List<GenreDTO> genreEntityList2DTOList(List<GenreEntity> entities){
        List<GenreDTO> dtos = new ArrayList<>();
        for (GenreEntity entity : entities) {
            dtos.add(this.genreEntity2DTO(entity));
        }
        return dtos;
    }
}
