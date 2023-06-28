package com.example.springkafka.domain.kafka.mapper;

import com.example.springkafka.domain.kafka.dto.KafkaRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaMapper {
    private final ObjectMapper om = new ObjectMapper();
    public String KafkaRequestDtoToStr(KafkaRequestDto kafkaRequestDto){
        try{
            return om.writeValueAsString(kafkaRequestDto);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public KafkaRequestDto strToKafkaRequestDto(String str) {
        try {
            return om.readValue(str, KafkaRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
