package com.alkemy.disney.repository;

import com.alkemy.disney.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long>, JpaSpecificationExecutor<MovieEntity> {
    List<MovieEntity> findAll(Specification<MovieEntity> spec);

}
