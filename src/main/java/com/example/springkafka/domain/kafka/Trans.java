package com.example.springkafka.domain.kafka;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.springkafka.domain.kafka.dto.protobuf.KafkaRequestProto;
import com.example.springkafka.domain.kafka.dto.protobuf.OrderKafkaDto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;

import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.SchemaProvider;
import io.confluent.kafka.schemaregistry.avro.AvroSchemaProvider;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
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
		String subject2 = "test";
		Descriptors.FileDescriptor descriptor = OrderKafkaDto.getDescriptor();
		Descriptors.FileDescriptor descriptor2 = KafkaRequestProto.getDescriptor();
		ProtobufSchema protobufSchema = new ProtobufSchema(descriptor);
		// ProtobufSchema protobufSchema2 = new ProtobufSchema(descriptor2);

		int schemaId = schemaRegistryClient.register(subject, protobufSchema);
		// System.out.println("Registered schema id: " + schemaId);

		// schemaRegistryClient.deleteSubject(subject);
		try {
			schemaRegistryClient.getSchemaById(6);
			ProtobufSchema retrievedSchema = (ProtobufSchema) schemaRegistryClient.getSchemaById(6);
			System.out.println("Retrieved Schema: " + retrievedSchema.rawSchema());
		} catch (IOException | RestClientException e) {
			e.printStackTrace();
		}
	}
}