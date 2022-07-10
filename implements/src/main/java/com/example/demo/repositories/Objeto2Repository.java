package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Objeto2;

@Repository
public interface Objeto2Repository extends JpaRepository<Objeto2, Integer>, JpaSpecificationExecutor<Objeto2>{

}
