package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.MovieBasicDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.entity.MovieEntity;
import com.alkemy.disney.entity.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class MovieMapper {

    @Autowired
    private CharacterMapper characterMapper;

    public MovieEntity movieDTO2Entity(MovieDTO dto){
        MovieEntity entity = new MovieEntity();
        entity.setImage(dto.getImage());
        entity.setTitle(dto.getTitle());
        entity.setCreationDate(this.string2LocalDate(dto.getCreationDate()));
        entity.setQualification(dto.getQualification());
        entity.setGenreId(dto.getGenreId());

        Set<CharacterEntity> characters = characterMapper.characterDTOList2EntitySet(dto.getCharacters());
        entity.setCharacters(characters);
        return entity;
    }

    public MovieDTO movieEntity2DTO(MovieEntity movieEntity, boolean loadCharacters){
        MovieDTO dto = new MovieDTO();
        dto.setId(movieEntity.getId());
        dto.setImage(movieEntity.getImage());
        dto.setTitle(movieEntity.getTitle());
        dto.setCreationDate(movieEntity.getCreationDate().toString());
        dto.setQualification(movieEntity.getQualification());
        dto.setGenreId(movieEntity.getGenreId());
        if(loadCharacters){
            List<CharacterDTO> charactersDTOS = characterMapper.characterEntitySet2DTOList(movieEntity.getCharacters(), false);
            dto.setCharacters(charactersDTOS);
        }
        return dto;
    }

    private LocalDate string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }

    public void movieEntityRefreshValues(MovieEntity movieEntity, MovieDTO movieDTO){
        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setCreationDate(this.string2LocalDate(movieDTO.getCreationDate()));
        movieEntity.setQualification(movieDTO.getQualification());
        movieEntity.setGenreId(movieDTO.getGenreId());

        Set<CharacterEntity> characters = characterMapper.characterDTOList2EntitySet(movieDTO.getCharacters());
        for (CharacterEntity characterEntity : characters) {
            movieEntity.getCharacters().add(characterEntity);
        }
    }

    public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> entities, boolean loadCharacters) {
        List<MovieDTO> dtos = new ArrayList<>();
        for (MovieEntity entity : entities) {
            dtos.add(movieEntity2DTO(entity, loadCharacters));
        }
        return dtos;
    }

    public MovieBasicDTO movieEntity2BasicDTO(MovieEntity entity){
        MovieBasicDTO dto = new MovieBasicDTO();
        dto.setImage(entity.getImage());
        dto.setTitle(entity.getTitle());
        LocalDate date = entity.getCreationDate();
        String formatDate = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        dto.setCreationDate(formatDate);
        return dto;
    }

    public  List<MovieBasicDTO> movieEntityList2BasicDTOList(List<MovieEntity> entities) {
        List<MovieBasicDTO> dtos = new ArrayList<>();
        for (MovieEntity movie: entities) {
            dtos.add(this.movieEntity2BasicDTO(movie));
        }
        return dtos;
    }


}
