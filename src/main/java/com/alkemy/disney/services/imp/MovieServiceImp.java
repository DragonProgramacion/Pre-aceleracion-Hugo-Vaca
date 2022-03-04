package com.alkemy.disney.services.imp;

import com.alkemy.disney.services.CharacterService;
import com.alkemy.disney.services.MovieService;
import com.alkemy.disney.dto.MovieBasicDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.MovieFiltersDTO;
import com.alkemy.disney.entity.MovieEntity;
import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.mapper.MovieMapper;
import com.alkemy.disney.repository.MovieRepository;
import com.alkemy.disney.repository.specifications.MovieSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImp implements MovieService {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieSpecification movieSpecification;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CharacterService characterService;

    public MovieEntity getEntityById(Long id) {
        Optional<MovieEntity> entity = movieRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFound("Movie id not found");
        }
        return entity.get();
    }

    public MovieDTO getDetailsById(Long id) {
        Optional<MovieEntity> entity = this.movieRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
        MovieDTO movieDTO = this.movieMapper.movieEntity2DTO(entity.get(),true);
        return movieDTO;
    }

    public List<MovieBasicDTO> getByFilters(String name, Set<Long> genres, String order) {
        MovieFiltersDTO filtersDto = new MovieFiltersDTO(name, genres,order);
        List<MovieEntity> entities = this.movieRepository.findAll(this.movieSpecification.getByFilters(filtersDto));
        List<MovieBasicDTO> dtos = this.movieMapper.movieEntityList2BasicDTOList(entities);
        return dtos;
    }


    public MovieDTO save(MovieDTO dto) {
        MovieEntity entity = this.movieMapper.movieDTO2Entity(dto);
        MovieEntity movieSaved = this.movieRepository.save(entity);
        MovieDTO result = this.movieMapper.movieEntity2DTO(movieSaved,true);
        return result;
    }

    public MovieDTO update(Long id , MovieDTO movieDTO) {
        Optional<MovieEntity> entity = this.movieRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
        this.movieMapper.movieEntityRefreshValues(entity.get(), movieDTO);
        MovieEntity entitySaved = this.movieRepository.save(entity.get());
        MovieDTO result = this.movieMapper.movieEntity2DTO(entitySaved, true);
        return result;
    }

    public void addCharacter(Long id, Long idCharacter){
        MovieEntity entity = getEntityById(id);
        Set<CharacterEntity> entities = entity.getCharacters();
        entities.add(characterService.getEntityById(idCharacter));
        entity.setCharacters(entities);
        this.movieRepository.save(entity);
    }

    public void removeCharacter(Long id, Long idCharacter){
        MovieEntity entity = getEntityById(id);
        Set<CharacterEntity> entities = entity.getCharacters();
        entities.remove(characterService.getEntityById(idCharacter));
        entity.setCharacters(entities);
        this.movieRepository.save(entity);
    }

    public void delete(Long id) {
        this.movieRepository.deleteById(id);
    }
}
