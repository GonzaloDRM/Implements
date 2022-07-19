package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.configurations.CustomEntityUpdater;
import com.example.demo.dtos.ObjetoDTO;
import com.example.demo.models.Objeto;
import com.example.demo.repositories.ObjetoRepository;
import com.example.demo.specifications.BasicSpecification;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class ObjetoService {
    
    @Autowired
    private final ObjetoRepository objetoRepository;
    private final CustomEntityUpdater customEntityUpdater;
    
    public ObjetoService (ObjetoRepository objetoRepository, CustomEntityUpdater customEntityUpdater) {
        this.objetoRepository = objetoRepository;
        this.customEntityUpdater = customEntityUpdater;
    }

    @Transactional(readOnly = true)
    public Page<Objeto> getObjetos(Pageable pageable){
        Specification<Objeto> objetoSpecification = BasicSpecification.vacio();
        Page<Objeto> resp = objetoRepository.findAll(objetoSpecification, pageable);
        return resp;
    }
    
    @Transactional
    public void save(ObjetoDTO objeto) {
        Objeto toSave = new Objeto();
        toSave.setName(objeto.getName());
        objetoRepository.save(toSave);
    }
    
    @Transactional
    public void update(ObjetoDTO objeto) throws JsonMappingException {
        Specification<Objeto> objetoSpecification = BasicSpecification.hasId(objeto.getId());
        Objeto toSave = objetoRepository.findOne(objetoSpecification).orElse(null);
        toSave = customEntityUpdater.sync(Objeto.class, toSave, objeto);
        objetoRepository.save(toSave);
    }
}
