package com.example.demo.specifications;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Objeto;
import com.example.demo.models.Objeto2;
import com.example.demo.models.Objeto2_;
import com.example.demo.models.Objeto_;

public class JoinSpecification {

    //join para pasar de un objeto a otro
    public static Specification<Objeto> hasAssetType(Integer objetoId) {
        return (root, query, criteriaBuilder) -> { 
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Objeto> subqueryRoot = subquery.from(Objeto.class);
            subquery.select(subqueryRoot.get(Objeto_.ID));
            
            Join<Objeto, Objeto2> objeto2Join = subqueryRoot.join(Objeto_.OBJETOS2);
            
            subquery.where(
                    criteriaBuilder.equal(subqueryRoot.get(Objeto_.ID), root.get(Objeto_.ID)),
                    criteriaBuilder.equal(objeto2Join.get(Objeto2_.ID), objetoId)
                    );
            return criteriaBuilder.exists(subquery);
        };
    }
    
    //join con distinct para no tener repetidos
    public static Specification<Objeto> hasProjectId(Integer objetoId) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Objeto> subqueryRoot = subquery.from(Objeto.class);
            subquery.select(subqueryRoot.get(Objeto_.ID));
            query.distinct(true);
            
            Join<Objeto, Objeto2> projectRoot = subqueryRoot.join(Objeto_.OBJETOS2, JoinType.INNER);
            
            subquery.where(
                    criteriaBuilder.equal(root.get(Objeto_.ID), subqueryRoot.get(Objeto_.ID)),
                    criteriaBuilder.equal(projectRoot.get(Objeto2_.ID), objetoId)
                    );
            return criteriaBuilder.exists(subquery);
        };
    }
    
}
