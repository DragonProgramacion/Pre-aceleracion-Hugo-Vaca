package com.alkemy.disney.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

public class MovieFiltersDTO {
    private String title;
    private Set<Long> genres;
    private String order;

    public Boolean isASC() {
        return this.order.compareToIgnoreCase("ASC") == 0;
    }

    public Boolean isDESC() {
        return this.order.compareToIgnoreCase("DESC") == 0;
    }

}
