package com.example.springkafka.domain.kafka.dto;

import lombok.Builder;

public record KafkaRequestDto(
        String name,
        int age,
        String company
) {
    @Builder
    public KafkaRequestDto {
    }
}
