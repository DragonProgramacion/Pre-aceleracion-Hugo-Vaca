package com.alkemy.disney.repository.specifications;

import com.alkemy.disney.dto.MovieFiltersDTO;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.entity.MovieEntity;
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
public class MovieSpecification {

    public Specification<MovieEntity> getByFilters (MovieFiltersDTO filtersDTO) {
        return ((root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filtersDTO.getTitle())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + filtersDTO.getTitle().toLowerCase() + "%"
                        )
                );

            }

            if (!CollectionUtils.isEmpty(filtersDTO.getGenres())) {
                Join<GenreEntity, MovieEntity> join = root.join("genres", JoinType.INNER);
                Expression<String> genreId = join.get("id");
                predicates.add(genreId.in(filtersDTO.getGenres()));
            }

            //remueve los duplicados
            query.distinct(true);

            //resuelve el order
            String orderByField = "title";
            query.orderBy(filtersDTO.isASC() ?
                    criteriaBuilder.asc(root.get(orderByField)) :
                    criteriaBuilder.desc(root.get(orderByField))
            );
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });

    }
}
