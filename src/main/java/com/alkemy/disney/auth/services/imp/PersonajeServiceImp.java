package com.alkemy.disney.auth.services.imp;

import com.alkemy.disney.auth.services.PeliculaService;
import com.alkemy.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.dto.PersonajeFiltersDTO;
import com.alkemy.disney.entity.PersonajeEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.mapper.PersonajeMapper;
import com.alkemy.disney.repository.PersonajeRepository;

import com.alkemy.disney.repository.specifications.PersonajeSpecification;
import com.alkemy.disney.auth.services.PersonajeService;
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
public class PersonajeServiceImp implements PersonajeService {

    @Autowired
    private PersonajeMapper personajeMapper;
    @Autowired
    private PersonajeSpecification personajeSpecification;
    @Autowired
    private PersonajeRepository personajeRepository;
    @Autowired
    private PeliculaService peliculaService;

    public PersonajeDTO getDetailsById(Long id) {
        Optional<PersonajeEntity> entity = this.personajeRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
       return this.personajeMapper.personajeEntity2DTO(entity.get(),true);
    }

    public List<PersonajeBasicDTO> getByFilters(String nombre, Integer edad, Set<Long> peliculas, String order) {
        PersonajeFiltersDTO filtersDTO = new PersonajeFiltersDTO(nombre,edad,peliculas,order);
        List<PersonajeEntity> entities = this.personajeRepository.findAll(this.personajeSpecification.getByFilters(filtersDTO));
        List<PersonajeBasicDTO> dtos = this.personajeMapper.personajeEntityList2BasicDTOList(entities);
        return dtos;
    }

    public PersonajeEntity getEntityById(Long id) {
        Optional<PersonajeEntity> entity= personajeRepository.findById(id);
        if (!entity.isPresent()){
            throw new ParamNotFound("ID character not found");
        }
        return entity.get();
    }

    public PersonajeDTO save(PersonajeDTO dto, Long idPelicula) {
        PersonajeEntity entity = personajeMapper.personajeDTO2Entity(dto);
        PersonajeEntity entitySaved = this.personajeRepository.save(entity);
        peliculaService.addPersonaje(idPelicula,entitySaved.getId());
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved, true);
        return result;
    }

    public PersonajeDTO update(Long id ,PersonajeDTO personajeDTO) {
        Optional<PersonajeEntity> entity = this.personajeRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
        this.personajeMapper.personajeEntityRefreshValues(entity.get(),personajeDTO);
        PersonajeEntity entitySaved = this.personajeRepository.save(entity.get());
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved,true);
        return result;
    }

    public void delete(Long id) {
        this.personajeRepository.deleteById(id);
    }

}
