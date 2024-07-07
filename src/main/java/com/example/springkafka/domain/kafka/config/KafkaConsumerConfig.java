package com.example.springkafka.domain.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.example.springkafka.domain.kafka.dto.protobuf.KafkaRequestProto;
import com.example.springkafka.domain.kafka.dto.protobuf.OrderKafkaDto;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig;

@Configuration
public class KafkaConsumerConfig {

	@Bean(name = "stringConsumerFactory")
	public ConsumerFactory<String, String> stringConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "string-group");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.EARLIEST.toString());
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean(name = "stringKafkaListener")
	public ConcurrentKafkaListenerContainerFactory<String, String> stringKafkaListener() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(stringConsumerFactory());
		return factory;
	}

	@Bean(name = "protobufConsumerFactory")
	public ConsumerFactory<String, byte[]> protobufConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "protobuf-group");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.EARLIEST.toString());
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean(name = "protobufKafkaListener")
	public ConcurrentKafkaListenerContainerFactory<String, byte[]> protubufKafkaListener() {
		ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(protobufConsumerFactory());
		return factory;
	}

	@Bean(name = "schemaConsumerFactory")
	public ConsumerFactory<String, KafkaRequestProto.KafkaRequestDto> schemaConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "schema-group");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaProtobufDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.EARLIEST.toString());
		props.put(KafkaProtobufDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
		props.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE,
			KafkaRequestProto.KafkaRequestDto.class.getName());
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean(name = "schemaKafkaListener")
	public ConcurrentKafkaListenerContainerFactory<String, KafkaRequestProto.KafkaRequestDto> schemaKafkaListener() {
		ConcurrentKafkaListenerContainerFactory<String, KafkaRequestProto.KafkaRequestDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(schemaConsumerFactory());
		return factory;
	}
}
