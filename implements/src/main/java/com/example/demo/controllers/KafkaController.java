package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.KafkaModel;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private KafkaTemplate<String, KafkaModel> kafkaTemplate;
    
    @Autowired
    public KafkaController(KafkaTemplate<String, KafkaModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    @PostMapping
    public void post(@RequestBody KafkaModel kafkaModel) {
        kafkaTemplate.send("Message", kafkaModel);        
    }
    
}
