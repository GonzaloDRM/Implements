package com.example.demo.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Objeto;

import cl.apptec.mobysuite.model.bien.Asset;
import cl.apptec.mobysuite.model.bien.Asset_;

public class BasicSpecification {
    
    public static Specification<Objeto> vacio() {
        return (root, query, criteriaBuilder) -> null;
    }

    public static Specification<Objeto> hasId(Integer objetoId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Objeto_.ID), objetoId);
    }
    
    public static Specification<Objeto> hasNoContract() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(Objeto_.DISPONIBLE));
    }
    
}
