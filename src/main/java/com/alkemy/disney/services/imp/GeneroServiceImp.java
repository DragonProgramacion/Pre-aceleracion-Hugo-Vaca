package com.alkemy.disney.services.imp;

import com.alkemy.disney.dto.GeneroDTO;
import com.alkemy.disney.entity.GeneroEntity;
import com.alkemy.disney.mapper.GeneroMapper;
import com.alkemy.disney.repository.GeneroRepository;
import com.alkemy.disney.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroServiceImp implements GeneroService {

    @Autowired
    private GeneroMapper generoMapper;
    @Autowired
    private GeneroRepository generoRepository;

    public GeneroDTO save(GeneroDTO dto) {
        GeneroEntity entity = generoMapper.generoDTO2Entity(dto);
        GeneroEntity generoSaved = generoRepository.save(entity);
        GeneroDTO result = generoMapper.generoEntity2DTO(generoSaved);
        return result;

    }

    public List<GeneroDTO> getAll() {
        List<GeneroEntity> entities = this.generoRepository.findAll();
        List<GeneroDTO> result = this.generoMapper.generoEntityList2DTOList(entities);
        return result;
    }

    public void delete(Long id) {
        this.generoRepository.deleteById(id);
    }


}
