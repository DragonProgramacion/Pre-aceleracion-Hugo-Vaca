package com.alkemy.disney.repository.specifications;

import com.alkemy.disney.dto.PersonajeFiltersDTO;
import com.alkemy.disney.entity.PeliculaEntity;
import com.alkemy.disney.entity.PersonajeEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonajeSpecification {

    public Specification<PersonajeEntity> getByFilters(PersonajeFiltersDTO filtersDTO) {
        //funcion lambda
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(!CollectionUtils.isEmpty(filtersDTO.getPeliculas())){
                Join<PeliculaEntity,PersonajeEntity> join = root.join("peliculas", JoinType.INNER);
                Expression<String> peliculasId = join.get("id");
                predicates.add(peliculasId.in(filtersDTO.getPeliculas()));
            }

            if (!ObjectUtils.isEmpty(filtersDTO.getEdad())){
                predicates.add(
                        criteriaBuilder.like(
                                root.get("edad").as(String.class),
                                "%" + filtersDTO.getEdad() + "%"
                        )
                );
            }
            if (StringUtils.hasLength(filtersDTO.getNombre())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("nombre")),
                                "%" + filtersDTO.getNombre().toLowerCase() + "%"
                        )
                );
            }
            //Remove duplicates
            query.distinct(true);

            //Order resolver
            String orderByField = "nombre";
            query.orderBy(
                    filtersDTO.isASC() ?
                        criteriaBuilder.asc(root.get(orderByField)):
                        criteriaBuilder.desc(root.get(orderByField))
            );
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
