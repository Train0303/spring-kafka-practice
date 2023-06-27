package com.example.springkafka.domain.kafka.controller;

import com.example.springkafka.domain.kafka.dto.KafkaRequestDto;
import com.example.springkafka.domain.kafka.service.KafkaSampleProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kafka")
public class KafkaTestcontraller {

    private final KafkaSampleProducerService kafkaSampleProducerService;

    @GetMapping("/test")
    public String test() {
        ObjectMapper mapper = new ObjectMapper();
        KafkaRequestDto message = KafkaRequestDto.builder()
                .name("김태호")
                .company("부산대학교")
                .age(26)
                .build();

        try{
            String jsonInString = mapper.writeValueAsString(message);
            kafkaSampleProducerService.sendMessage(jsonInString);
            return "Send Success";
        } catch(IOException e) {
            e.printStackTrace();
        }

        return "Send failed";
    }

    @GetMapping("/test/callback")
    public void testCallback(){
        ObjectMapper mapper = new ObjectMapper();
        KafkaRequestDto message = KafkaRequestDto.builder()
                .name("김태호")
                .company("부산대학교")
                .age(26)
                .build();

        try{
            String jsonInString = mapper.writeValueAsString(message);
            kafkaSampleProducerService.sendMessageAndCallback(jsonInString);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}
