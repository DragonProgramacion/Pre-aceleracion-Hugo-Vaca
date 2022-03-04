package com.alkemy.disney.repository.specifications;

import com.alkemy.disney.dto.CharacterFiltersDTO;
import com.alkemy.disney.entity.MovieEntity;
import com.alkemy.disney.entity.CharacterEntity;
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
public class CharacterSpecification {

    public Specification<CharacterEntity> getByFilters(CharacterFiltersDTO filtersDTO) {
        //funcion lambda
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(!CollectionUtils.isEmpty(filtersDTO.getMovies())){
                Join<MovieEntity, CharacterEntity> join = root.join("movies", JoinType.INNER);
                Expression<String> moviesId = join.get("id");
                predicates.add(moviesId.in(filtersDTO.getMovies()));
            }

            if (!ObjectUtils.isEmpty(filtersDTO.getAge())){
                predicates.add(
                        criteriaBuilder.like(
                                root.get("age").as(String.class),
                                "%" + filtersDTO.getAge() + "%"
                        )
                );
            }
            if (StringUtils.hasLength(filtersDTO.getName())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + filtersDTO.getName().toLowerCase() + "%"
                        )
                );
            }
            //Remove duplicates
            query.distinct(true);

            //Order resolver
            String orderByField = "name";
            query.orderBy(
                    filtersDTO.isASC() ?
                        criteriaBuilder.asc(root.get(orderByField)):
                        criteriaBuilder.desc(root.get(orderByField))
            );
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
