package com.alkemy.disney.auth.services.imp;

import com.alkemy.disney.auth.services.PeliculaService;
import com.alkemy.disney.auth.services.PersonajeService;
import com.alkemy.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.PeliculaFiltersDTO;
import com.alkemy.disney.entity.PeliculaEntity;
import com.alkemy.disney.entity.PersonajeEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.mapper.PeliculaMapper;
import com.alkemy.disney.repository.PeliculaRepository;
import com.alkemy.disney.repository.specifications.PeliculaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PeliculaServiceImp implements PeliculaService {

    @Autowired
    private PeliculaMapper peliculaMapper;
    @Autowired
    private PeliculaSpecification peliculaSpecification;
    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private PersonajeService personajeService;

    public PeliculaEntity getEntityById(Long id) {
        Optional<PeliculaEntity> entity = peliculaRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFound("Movie id not found");
        }
        return entity.get();
    }

    public PeliculaDTO getDetailsById(Long id) {
        Optional<PeliculaEntity> entity = this.peliculaRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
        PeliculaDTO peliculaDTO = this.peliculaMapper.peliculaEntity2DTO(entity.get(),true);
        return peliculaDTO;
    }

    public List<PeliculaBasicDTO> getByFilters(String titulo, Set<Long> personajes, String order) {
        PeliculaFiltersDTO filtersDto = new PeliculaFiltersDTO(titulo,personajes,order);
        List<PeliculaEntity> entities = this.peliculaRepository.findAll(this.peliculaSpecification.getByFilters(filtersDto));
        List<PeliculaBasicDTO> dtos = this.peliculaMapper.peliculaEntityList2BasicDTOList(entities);
        return dtos;
    }


    public PeliculaDTO save(PeliculaDTO dto) {
        PeliculaEntity entity = this.peliculaMapper.peliculaDTO2Entity(dto);
        PeliculaEntity peliculaSaved = this.peliculaRepository.save(entity);
        PeliculaDTO result = this.peliculaMapper.peliculaEntity2DTO(peliculaSaved,true);
        return result;
    }

    public PeliculaDTO update(Long id , PeliculaDTO peliculaDTO) {
        Optional<PeliculaEntity> entity = this.peliculaRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
        this.peliculaMapper.peliculaEntityRefreshValues(entity.get(), peliculaDTO);
        PeliculaEntity entitySaved = this.peliculaRepository.save(entity.get());
        PeliculaDTO result = this.peliculaMapper.peliculaEntity2DTO(entitySaved, true);
        return result;
    }

    public void addPersonaje(Long id, Long idPersonaje){
        PeliculaEntity entity = getEntityById(id);
        Set<PersonajeEntity> entities = entity.getPersonajes();
        entities.add(personajeService.getEntityById(idPersonaje));
        entity.setPersonajes(entities);
        this.peliculaRepository.save(entity);
    }

    public void removePersonaje(Long id, Long idPersonaje){
        PeliculaEntity entity = getEntityById(id);
        Set<PersonajeEntity> entities = entity.getPersonajes();
        entities.remove(personajeService.getEntityById(idPersonaje));
        entity.setPersonajes(entities);
        this.peliculaRepository.save(entity);
    }

    public void delete(Long id) {
        this.peliculaRepository.deleteById(id);
    }
}
