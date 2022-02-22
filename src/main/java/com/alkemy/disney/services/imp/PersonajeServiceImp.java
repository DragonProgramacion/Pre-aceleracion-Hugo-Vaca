package com.alkemy.disney.services.imp;

import com.alkemy.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.dto.PersonajeFiltersDTO;
import com.alkemy.disney.entity.PeliculaEntity;
import com.alkemy.disney.entity.PersonajeEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.mapper.PersonajeMapper;
import com.alkemy.disney.repository.PersonajeRepository;

import com.alkemy.disney.repository.specifications.PersonajeSpecification;
import com.alkemy.disney.services.PeliculaService;
import com.alkemy.disney.services.PersonajeService;
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

    @Override
    public PersonajeEntity getById(Long id) {
        return null;
    }

    public PersonajeDTO getDetailsById(Long id) {
        Optional<PersonajeEntity> entity = this.personajeRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
        PersonajeDTO personajeDTO = this.personajeMapper.personajeEntity2DTO(entity.get(),true);
        return personajeDTO;
    }

    public List<PersonajeBasicDTO> getByFilters(String nombre, Integer edad, Set<Long> peliculas, String order) {
        PersonajeFiltersDTO filtersDTO = new PersonajeFiltersDTO(nombre,edad,peliculas,order);
        List<PersonajeEntity> entities = this.personajeRepository.findAll(this.personajeSpecification.getByFilters(filtersDTO));
        List<PersonajeBasicDTO> dtos = this.personajeMapper.personajeEntityList2BasicDTOList(entities);
        return dtos;
    }

    public PersonajeDTO save(PersonajeDTO dto) {
        PersonajeEntity entity = personajeMapper.personajeDTO2Entity(dto);
        PersonajeEntity entitySaved = this.personajeRepository.save(entity);
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved, false);
        return result;
    }

    public PersonajeDTO update(Long id ,PersonajeDTO personajeDTO) {
        Optional<PersonajeEntity> entity = this.personajeRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
        this.personajeMapper.personajeEntityRefreshValues(entity.get(),personajeDTO);
        PersonajeEntity entitySaved = this.personajeRepository.save(entity.get());
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved,false);
        return result;
    }

    public void addPelicula(Long id , Long idPelicula){
        PersonajeEntity entity = this.personajeRepository.getById(id);
        entity.getPeliculas().size();
        PeliculaEntity peliculaEntity = this.peliculaService.getById(idPelicula);
        entity.addPelicula(peliculaEntity);
        this.personajeRepository.save(entity);
    }

    public void removePelicula(Long id , Long idPelicula){
        PersonajeEntity entity = this.personajeRepository.getById(id);
        entity.getPeliculas().size();
        PeliculaEntity peliculaEntity = this.peliculaService.getById(idPelicula);
        entity.removePelicula(peliculaEntity);
        this.personajeRepository.save(entity);
    }

    public void delete(Long id) {
        this.personajeRepository.deleteById(id);
    }

}
