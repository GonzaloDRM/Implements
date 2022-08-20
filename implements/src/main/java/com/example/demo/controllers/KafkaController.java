package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.KafkaModel;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private KafkaTemplate<String, KafkaModel> kafkaTemplate;
    private KafkaTemplate<String, String> kafkaTemplateWithGson;
    private Gson jsonConverter;
    
    @Autowired
    public KafkaController(KafkaTemplate<String, KafkaModel> kafkaTemplate, KafkaTemplate<String, String> kafkaTemplateWithGson, Gson jsonConverter) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateWithGson = kafkaTemplateWithGson;
        this.jsonConverter = jsonConverter;
    }
    
    @PostMapping 
    public void post(@RequestBody KafkaModel kafkaModel) {
        kafkaTemplate.send("Message", kafkaModel);        
    }
    
    @KafkaListener(topics = "myTopic")
    public void getFromKafka(KafkaModel kafkaModel) {
        System.out.println(kafkaModel.toString());
    }
    
    //With Gson library -----------------------------------------------------------------------------
    
    @PostMapping
    public void postWithGson(@RequestBody KafkaModel kafkaModel) {
        kafkaTemplateWithGson.send("Message", jsonConverter.toJson(kafkaModel));
    }
    
    @KafkaListener(topics = "myTopic")
    public void getFromKafkaWithGson(String kafkaModel) {
        System.out.println(kafkaModel);
        KafkaModel kafkaModel1 = (KafkaModel) jsonConverter.fromJson(kafkaModel, KafkaModel.class);
        System.out.println(kafkaModel1.toString());
    }
    
}
