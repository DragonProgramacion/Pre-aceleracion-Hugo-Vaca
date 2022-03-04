package com.alkemy.disney.services.imp;

import com.alkemy.disney.dto.CharacterFiltersDTO;
import com.alkemy.disney.services.CharacterService;
import com.alkemy.disney.services.MovieService;
import com.alkemy.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.mapper.CharacterMapper;
import com.alkemy.disney.repository.CharacterRepository;

import com.alkemy.disney.repository.specifications.CharacterSpecification;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class CharacterServiceImp implements CharacterService {

    @Autowired
    private CharacterMapper characterMapper;
    @Autowired
    private CharacterSpecification characterSpecification;
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private MovieService movieService;

    public CharacterDTO getDetailsById(Long id) {
        Optional<CharacterEntity> entity = this.characterRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
       return this.characterMapper.characterEntity2DTO(entity.get(),true);
    }

    public List<CharacterBasicDTO> getByFilters(String name, Integer age, Set<Long> movies, String order) {
        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name,age,movies,order);
        List<CharacterEntity> entities = this.characterRepository.findAll(this.characterSpecification.getByFilters(filtersDTO));
        List<CharacterBasicDTO> dtos = this.characterMapper.characterEntityList2BasicDTOList(entities);
        return dtos;
    }

    public CharacterEntity getEntityById(Long id) {
        Optional<CharacterEntity> entity= characterRepository.findById(id);
        if (!entity.isPresent()){
            throw new ParamNotFound("ID character not found");
        }
        return entity.get();
    }

    public CharacterDTO save(CharacterDTO dto, Long idMovie) {
        CharacterEntity entity = characterMapper.characterDTO2Entity(dto);
        CharacterEntity entitySaved = this.characterRepository.save(entity);
        movieService.addCharacter(idMovie,entitySaved.getId());
        CharacterDTO result = this.characterMapper.characterEntity2DTO(entitySaved, true);
        return result;
    }

    public CharacterDTO update(Long id , CharacterDTO characterDTO) {
        Optional<CharacterEntity> entity = this.characterRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
        this.characterMapper.characterEntityRefreshValues(entity.get(), characterDTO);
        CharacterEntity entitySaved = this.characterRepository.save(entity.get());
        CharacterDTO result = this.characterMapper.characterEntity2DTO(entitySaved,true);
        return result;
    }

    public void delete(Long id) {
        this.characterRepository.deleteById(id);
    }

}
