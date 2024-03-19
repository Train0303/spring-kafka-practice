package com.example.springkafka.domain.kafka.controller;

import com.example.springkafka.domain.kafka.dto.KafkaRequestDto;
import com.example.springkafka.domain.kafka.dto.protobuf.KafkaRequestProto;
import com.example.springkafka.domain.kafka.mapper.KafkaMapper;
import com.example.springkafka.domain.kafka.service.KafkaSampleProducerService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kafka")
public class KafkaTestcontraller {

    private final KafkaSampleProducerService kafkaSampleProducerService;
    private final KafkaMapper kafkaMapper;

    @GetMapping("/test")
    public void test() {
        KafkaRequestDto message = KafkaRequestDto.builder()
                .name("김태호")
                .company("부산대학교")
                .age(26)
                .build();

        String jsonInString = kafkaMapper.KafkaRequestDtoToStr(message);
        kafkaSampleProducerService.sendMessage(jsonInString);
    }


    @GetMapping("/test/callback")
    public void testCallback(){
        KafkaRequestDto message = KafkaRequestDto.builder()
                .name("김태호")
                .company("부산대학교")
                .age(26)
                .build();

        String jsonInString = kafkaMapper.KafkaRequestDtoToStr(message);
        kafkaSampleProducerService.sendMessageAndCallback(jsonInString);
    }

    @GetMapping("/protobuf/test/callback")
    public void testProtoBufCallback(){
        KafkaRequestProto.KafkaRequestDto request = KafkaRequestProto.KafkaRequestDto.newBuilder()
            .setName("김태호")
            .setCompany("부산대학교")
            .setAge(26)
            .build();

        kafkaSampleProducerService.sendProtoMessageAndCallback(request);
    }
}
