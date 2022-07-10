package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Objeto;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Integer>, JpaSpecificationExecutor<Objeto>{

}
