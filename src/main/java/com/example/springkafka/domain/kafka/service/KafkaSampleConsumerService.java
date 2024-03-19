package com.example.springkafka.domain.kafka.service;

import com.example.springkafka.domain.kafka.dto.KafkaRequestDto;
import com.example.springkafka.domain.kafka.dto.protobuf.KafkaRequestProto;
import com.example.springkafka.domain.kafka.mapper.KafkaMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaSampleConsumerService {

    private final KafkaMapper kafkaMapper;

    @KafkaListener(topics = "testTopic", containerFactory = "stringKafkaListener")
    public void consumer(String receiveData) {
        System.out.println("--- 컨슈머 ---");
        System.out.println("컨슈머 쓰레드 ID : " + Thread.currentThread().getId());

        KafkaRequestDto data = kafkaMapper.strToKafkaRequestDto(receiveData);
        System.out.println("해당 메시지는 test_topic에서 온 메시지입니다.");
        System.out.println(data);
    }

    @KafkaListener(topics = "testTopicProto", containerFactory = "protobufKafkaListener")
    public void consumer(byte[] receiveData) throws InvalidProtocolBufferException {
        System.out.println("--- 컨슈머 ---");
        System.out.println("컨슈머 쓰레드 ID : " + Thread.currentThread().getId());

        KafkaRequestProto.KafkaRequestDto data = KafkaRequestProto.KafkaRequestDto.parseFrom(receiveData);

        System.out.println("해당 메시지는 test_topic_proto에서 온 메시지입니다.");
        System.out.println(JsonFormat.printer().print(data));
    }
}
