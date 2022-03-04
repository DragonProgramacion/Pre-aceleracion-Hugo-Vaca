package com.alkemy.disney.repository;

import com.alkemy.disney.entity.CharacterEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {

    List<CharacterEntity> findAll(Specification<CharacterEntity> spec);
}
