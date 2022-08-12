package com.example.demo.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Objeto;
import com.example.demo.models.Objeto_;

public class SortSpecification {

    public static Specification<Objeto> sortAnswerByOrderDate() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get(Objeto_.NAME)));
            return null;
        };
    }
    
}
