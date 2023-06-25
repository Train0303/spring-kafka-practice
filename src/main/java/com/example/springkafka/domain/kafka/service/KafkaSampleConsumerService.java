package com.example.springkafka.domain.kafka.service;

import com.example.springkafka.domain.kafka.dto.KafkaRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaSampleConsumerService {

    @KafkaListener(topics = "test_topic", groupId = "group-id-taeho")
    public void consume(String message){
        ObjectMapper mapper = new ObjectMapper();

        try{
            KafkaRequestDto data = mapper.readValue(message, KafkaRequestDto.class);
            System.out.println("해당 메시지는 test_topic에서 온 메시지입니다.");
            System.out.println(data);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
