package com.example.springkafka.domain.kafka.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaSampleProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send("test_topic", message);
    }

    public void sendMessageAndCallback(String message) {
        // 스프링 3.0부턴 CompletableFuture로 변경되었다.
//        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test_topic", message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("test_topic", message);
        future.whenComplete((result, ex) -> {
            if(ex == null) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                System.out.println("전송이 성공적으로 완료되었습니다.");
                System.out.println(result);
            }else{
                ex.printStackTrace();
            }
        });

    }
}
