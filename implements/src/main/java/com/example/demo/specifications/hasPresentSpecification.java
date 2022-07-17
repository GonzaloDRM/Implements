package com.example.demo.specifications;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Objeto;
import com.example.demo.models.Objeto_;

public class hasPresentSpecification {
    
    public static Specification<Objeto> hasObjeto2Present() {
        return (root, query, criteriaBuilder) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Objeto> subqueryRoot = subquery.from(Objeto.class);
            subquery.select(subqueryRoot.get(Objeto_.ID));

            subqueryRoot.join(Objeto_.OBJETOS2);

            return criteriaBuilder.exists(subquery);
        };
    }
}
