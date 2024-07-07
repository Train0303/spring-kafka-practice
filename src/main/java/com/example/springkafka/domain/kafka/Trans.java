package com.example.springkafka.domain.kafka;

import java.io.IOException;
import java.util.List;

import com.example.springkafka.domain.kafka.dto.protobuf.KafkaRequestProto;
import com.example.springkafka.domain.kafka.dto.protobuf.OrderKafkaDto;
import com.google.protobuf.Descriptors;

import io.confluent.kafka.schemaregistry.SchemaProvider;
import io.confluent.kafka.schemaregistry.avro.AvroSchemaProvider;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchema;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchemaProvider;

public class Trans {
	public static void main(String[] args) throws IOException, RestClientException {
		createSchema();
	}
	public static void createSchema() throws IOException, RestClientException {
		String schemaRegistryUrl = "http://localhost:8081";
		final List<SchemaProvider> schemaProviders = List.of(
			new AvroSchemaProvider(),
			new ProtobufSchemaProvider());
		CachedSchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(schemaRegistryUrl, 100, schemaProviders, null);
		// CachedSchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(schemaRegistryUrl, 100);
		String subject = "order";
		String subject2 = "testTopicProtoSchema-value";
		Descriptors.FileDescriptor descriptor = OrderKafkaDto.getDescriptor();
		Descriptors.FileDescriptor descriptor2 = KafkaRequestProto.getDescriptor();
		ProtobufSchema protobufSchema = new ProtobufSchema(descriptor);
		ProtobufSchema protobufSchema2 = new ProtobufSchema(descriptor2);

		// int schemaId = schemaRegistryClient.register(subject, protobufSchema);
		int schemaId2 = schemaRegistryClient.register(subject2, protobufSchema2);
		System.out.println("Registered schema id: " + schemaId2);

		// schemaRegistryClient.deleteSubject(subject);
		try {
			// schemaRegistryClient.getSchemaById(1);
			ProtobufSchema retrievedSchema = (ProtobufSchema) schemaRegistryClient.getSchemaById(2);
			System.out.println("Retrieved Schema: " + retrievedSchema.rawSchema());
		} catch (IOException | RestClientException e) {
			e.printStackTrace();
		}
	}
}