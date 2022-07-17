package com.example.demo.specifications;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Objeto;
import com.example.demo.models.Objeto2_;
import com.example.demo.models.Objeto_;

public class FetchSpecification {
    
    //traer objetos que estan dentro de objetos
    public static Specification<Objeto> fetchObjeto2() {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                query.distinct(true);
                root.fetch(Objeto_.OBJETOS2, JoinType.INNER);
            }
            return null;
        };
    }
    
    //tambien se puede traer objetos dentro de objetos agregando fetchs
    public static Specification<Objeto> fetchObjeto3() {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                query.distinct(true);
                root.fetch(Objeto_.OBJETOS2, JoinType.INNER).fetch(Objeto2_.OBJETO3, JoinType.INNER);
            }
            return null;
        };
    }
    
}
