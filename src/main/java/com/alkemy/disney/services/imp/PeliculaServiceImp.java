package com.alkemy.disney.services.imp;

import com.alkemy.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.PeliculaFiltersDTO;
import com.alkemy.disney.entity.PeliculaEntity;
import com.alkemy.disney.entity.PersonajeEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.mapper.PeliculaMapper;
import com.alkemy.disney.repository.PeliculaRepository;
import com.alkemy.disney.repository.specifications.PeliculaSpecification;
import com.alkemy.disney.services.PeliculaService;
import com.alkemy.disney.services.PersonajeService;
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

    @Override
    public PeliculaEntity getById(Long id) {
        return null;
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
        PeliculaDTO result = this.peliculaMapper.peliculaEntity2DTO(peliculaSaved,false);
        return result;

    }

    public PeliculaDTO update(Long id , PeliculaDTO peliculaDTO) {
        Optional<PeliculaEntity> entity = this.peliculaRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("invalid character id");
        }
        this.peliculaMapper.peliculaEntityRefreshValues(entity.get(), peliculaDTO);
        PeliculaEntity entitySaved = this.peliculaRepository.save(entity.get());
        PeliculaDTO result = this.peliculaMapper.peliculaEntity2DTO(entitySaved, false);
        return result;
    }

    public void addPersonaje(Long id, Long idPersonaje){
        PeliculaEntity entity = this.peliculaRepository.getById(id);
        entity.getPersonajes().size();
        PersonajeEntity personajeEntity = this.personajeService.getById(idPersonaje);
        entity.addPersonaje(personajeEntity);
        this.peliculaRepository.save(entity);
    }

    public void removePersonaje(Long id, Long idPersonaje){
        PeliculaEntity entity = this.peliculaRepository.getById(id);
        entity.getPersonajes().size();
        PersonajeEntity personajeEntity = this.personajeService.getById(idPersonaje);
        entity.removePersonaje(personajeEntity);
        this.peliculaRepository.save(entity);
    }

    public void delete(Long id) {
        this.peliculaRepository.deleteById(id);
    }
}
