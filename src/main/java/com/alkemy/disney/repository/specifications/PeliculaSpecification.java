package com.alkemy.disney.repository.specifications;

import com.alkemy.disney.dto.PeliculaFiltersDTO;
import com.alkemy.disney.entity.GeneroEntity;
import com.alkemy.disney.entity.PeliculaEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

@Component
public class PeliculaSpecification {

    public Specification<PeliculaEntity> getByFilters (PeliculaFiltersDTO filtersDTO) {
        return ((root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filtersDTO.getTitulo())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("titulo")),
                                "%" + filtersDTO.getTitulo().toLowerCase() + "%"
                        )
                );

            }

            if (!CollectionUtils.isEmpty(filtersDTO.getGeneros())) {
                Join<GeneroEntity, PeliculaEntity> join = root.join("generos", JoinType.INNER);
                Expression<String> generoId = join.get("id");
                predicates.add(generoId.in(filtersDTO.getGeneros()));
            }

            //remueve los duplicados
            query.distinct(true);

            //resuelve el order
            String orderByField = "titulo";
            query.orderBy(filtersDTO.isASC() ?
                    criteriaBuilder.asc(root.get(orderByField)) :
                    criteriaBuilder.desc(root.get(orderByField))
            );
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });

    }
}
