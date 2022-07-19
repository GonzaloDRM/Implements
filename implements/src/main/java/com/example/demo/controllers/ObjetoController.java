package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.ObjetoDTO;
import com.example.demo.models.Objeto;
import com.example.demo.services.ObjetoService;

@RestController
public class ObjetoController {

    @Autowired
    private final ObjetoService objetoService;
    private final ModelMapper modelMapper;
    
    public ObjetoController (ObjetoService objetoService, ModelMapper modelMapper) {
        this.objetoService = objetoService;
        this.modelMapper = modelMapper;
    }
    
    @GetMapping(value = "/api/get-objetos")
    public ResponseEntity<?> get(Pageable pageable){
        Page<Objeto> resp = objetoService.getObjetos(pageable);
        return ResponseEntity.ok(resp.map(e -> modelMapper.map(e, ObjetoDTO.class)));
    }
    
    @PostMapping(value = "/api/save-objeto")
    public ResponseEntity<?> post(@RequestBody ObjetoDTO objeto){
        Map<String, String> response = new HashMap<>();
        if (objeto == null) {
            response.put("message", "The object is null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        objetoService.save(objeto);
        response.put("message", "Upload sucessfull");
        return ResponseEntity.ok(response);
    }
    
}
