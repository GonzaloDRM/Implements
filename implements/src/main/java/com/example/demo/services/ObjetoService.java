package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Objeto;
import com.example.demo.repositories.ObjetoRepository;
import com.example.demo.specifications.BasicSpecification;

@Service
public class ObjetoService {
    
    @Autowired
    private final ObjetoRepository objetoRepository;
    
    public ObjetoService (ObjetoRepository objetoRepository) {
        this.objetoRepository = objetoRepository;
    }

    @Transactional(readOnly = true)
    public Page<Objeto> getObjetos(Pageable pageable){
        Specification<Objeto> objetoSpecification = BasicSpecification.vacio();
        Page<Objeto> resp = objetoRepository.findAll(objetoSpecification, pageable);
        return resp;
    }
    
}
