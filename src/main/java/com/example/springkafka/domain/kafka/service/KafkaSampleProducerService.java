package com.example.springkafka.domain.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import com.example.springkafka.domain.kafka.dto.protobuf.KafkaRequestProto;

@Service
public class KafkaSampleProducerService {
    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private final KafkaTemplate<String, byte[]> protobufKafkaTemplate;
    private final KafkaTemplate<String, KafkaRequestProto.KafkaRequestDto> schemaKafkaTemplate;

    @Autowired
    public KafkaSampleProducerService(
        @Qualifier("stringKafkaTemplate") KafkaTemplate<String, String> stringKafkaTemplate,
        @Qualifier("protobufKafkaTemplate") KafkaTemplate<String, byte[]> protobufKafkaTemplate,
        @Qualifier("schemaKafkaTemplate") KafkaTemplate<String, KafkaRequestProto.KafkaRequestDto> schemaKafkaTemplate) {
        this.stringKafkaTemplate = stringKafkaTemplate;
        this.protobufKafkaTemplate = protobufKafkaTemplate;
        this.schemaKafkaTemplate = schemaKafkaTemplate;
    }

    public void sendMessage(String message) {
        stringKafkaTemplate.send("testTopic", message);
    }

    public void sendMessageAndCallback(String message) {

        System.out.println("--- 프로듀서 ---");
        System.out.println("프로듀서 쓰레드 ID: " + Thread.currentThread().getId());
        System.out.println("데이터 크기: " + message.getBytes(StandardCharsets.UTF_8).length);
        // 스프링 3.0부턴 CompletableFuture로 변경되었다.
       // ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test_topic", message);
        CompletableFuture<SendResult<String, String>> future = stringKafkaTemplate.send("testTopic", message);

        // 콜백
        future.whenComplete((result, ex) -> {
            if(ex == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("---- 콜백 ----");
                System.out.println("콜백 쓰레드 ID: " + Thread.currentThread().getId());
                System.out.println("전송이 성공적으로 완료되었습니다.");
                System.out.println(result);
            }else{
                System.out.println("예외가 발생했습니다.");
                ex.printStackTrace();
            }
        });

       System.out.println(Thread.currentThread().getId());
    }

    public void sendProtoMessageAndCallback(KafkaRequestProto.KafkaRequestDto request) {

        System.out.println("--- 프로듀서 ---");
        System.out.println("프로듀서 쓰레드 ID: " + Thread.currentThread().getId());
        System.out.println("데이터 크기: " + request.toByteArray().length);
        // 스프링 3.0부턴 CompletableFuture로 변경되었다.
        //        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test_topic", message);
        CompletableFuture<SendResult<String, byte[]>> future = protobufKafkaTemplate.send("testTopicProto", request.toByteArray());
        // 콜백
        future.whenComplete((result, ex) -> {
            if(ex == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("---- 콜백 ----");
                System.out.println("콜백 쓰레드 ID: " + Thread.currentThread().getId());
                System.out.println("전송이 성공적으로 완료되었습니다.");
                System.out.println(result);
            }else{
                System.out.println("예외가 발생했습니다.");
                ex.printStackTrace();
            }
        });

    }

    public void sendProtoSchemaMessageAndCallback(KafkaRequestProto.KafkaRequestDto request) {

        System.out.println("--- 프로듀서 ---");
        System.out.println("프로듀서 쓰레드 ID: " + Thread.currentThread().getId());
        System.out.println(request.getSerializedSize());
        CompletableFuture<SendResult<String, KafkaRequestProto.KafkaRequestDto>> future
            = schemaKafkaTemplate.send("testTopicProtoSchema", request);

        // 콜백
        future.whenComplete((result, ex) -> {
            if(ex == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("---- 콜백 ----");
                System.out.println("콜백 쓰레드 ID: " + Thread.currentThread().getId());
                System.out.println("전송이 성공적으로 완료되었습니다.");
                System.out.println(result);
            }else{
                System.out.println("예외가 발생했습니다.");
                ex.printStackTrace();
            }
        });

    }
}
