package com.alkemy.disney.repository;

import com.alkemy.disney.entity.PersonajeEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonajeRepository extends JpaRepository<PersonajeEntity, Long> {

    List<PersonajeEntity> findAll(Specification<PersonajeEntity> spec);
}
