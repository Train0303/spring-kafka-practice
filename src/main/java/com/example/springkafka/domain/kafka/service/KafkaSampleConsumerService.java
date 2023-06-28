package com.example.springkafka.domain.kafka.service;

import com.example.springkafka.domain.kafka.dto.KafkaRequestDto;
import com.example.springkafka.domain.kafka.mapper.KafkaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class KafkaSampleConsumerService {

    private final KafkaMapper kafkaMapper;

    @KafkaListener(topics = "test_topic", groupId = "group-id-taeho")
    public void consumer(String message){
//        System.out.println("--- 컨슈머 ---");
//        System.out.println(Thread.currentThread().getId());
//
            KafkaRequestDto data = kafkaMapper.strToKafkaRequestDto(message);
            System.out.println("해당 메시지는 test_topic에서 온 메시지입니다.");
            System.out.println(data);
    }
}
