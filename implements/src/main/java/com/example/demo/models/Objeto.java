package com.example.demo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "OBJETO")
@Data
public class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NAME")
    private Double age;

    @Column(name = "FRIENDS")
    private List<String> friends;
    
    @Column(name = "OBJETOS2")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "Objeto", targetEntity = Objeto2.class )
    private List<Objeto2> objetos2;
    
    @Column(name = "ACTIVO")
    private Boolean activo;
}
