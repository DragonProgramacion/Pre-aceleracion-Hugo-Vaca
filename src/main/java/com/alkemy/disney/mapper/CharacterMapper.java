package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.entity.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharacterMapper {

    @Autowired
    private MovieMapper movieMapper;

    public CharacterEntity characterDTO2Entity(CharacterDTO dto){
        CharacterEntity entity = new CharacterEntity();
        entity.setImage(dto.getImage());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setWeight(dto.getWeight());
        entity.setStory(dto.getStory());
        return entity;
    }

    public CharacterDTO characterEntity2DTO(CharacterEntity characterEntity, boolean loadMovies){
        CharacterDTO dto = new CharacterDTO();
        dto.setId(characterEntity.getId());
        dto.setName(characterEntity.getName());
        dto.setImage(characterEntity.getImage());
        dto.setAge(characterEntity.getAge());
        dto.setWeight(characterEntity.getWeight());
        dto.setStory(characterEntity.getStory());

        if(loadMovies){
            List<MovieDTO> moviesDTO = this.movieMapper.movieEntityList2DTOList(characterEntity.getMovies(), false);
            dto.setMovies(moviesDTO);
        }
        return dto;
    }

    public void characterEntityRefreshValues(CharacterEntity characterEntity, CharacterDTO characterDTO){
        characterEntity.setImage(characterDTO.getImage());
        characterEntity.setName(characterDTO.getName());
        characterEntity.setAge(characterDTO.getAge());
        characterEntity.setWeight(characterDTO.getWeight());
        characterEntity.setStory(characterDTO.getStory());
    }

    public List<CharacterDTO> characterEntitySet2DTOList(Collection<CharacterEntity> entities, boolean loadMovies) {
        List<CharacterDTO> dtos = new ArrayList<>();
        for (CharacterEntity entity : entities) {
            dtos.add(characterEntity2DTO(entity, loadMovies));
        }
        return dtos;
    }

    public Set<CharacterEntity> characterDTOList2EntitySet(List<CharacterDTO> dtos) {
        Set<CharacterEntity> entities = new HashSet<>();
        for (CharacterDTO dto : dtos) {
            entities.add(characterDTO2Entity(dto));
        }
        return entities;
    }
    
    public CharacterBasicDTO characterEntity2BasicDTO(CharacterEntity entity) {
        CharacterBasicDTO dto = new CharacterBasicDTO();
        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setName(entity.getName());
        return dto;
    }

    public List<CharacterBasicDTO> characterEntityList2BasicDTOList(List<CharacterEntity> entities){
        List<CharacterBasicDTO> dtos = new ArrayList<>();
        for(CharacterEntity character : entities){
            dtos.add(this.characterEntity2BasicDTO(character));
        }
        return dtos;
    }
}
