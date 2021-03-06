package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Objeto2 {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "OBJETO_ID")
    @ManyToOne(targetEntity = Objeto.class, optional = false, fetch = FetchType.LAZY)
    private Objeto objeto;
    
    @Column(name = "OBJETO3")
    @OneToOne(targetEntity = Objeto.class, optional = false, fetch = FetchType.LAZY)
    private Objeto3 objeto3;
    
}
