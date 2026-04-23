package com.nagp.microservices.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


@Configuration
public class DynamoDbConfig {

	@Bean
	public DynamoDbClient dynamoDbClient() {
		return DynamoDbClient.builder()
				.region(Region.AP_SOUTH_1)
				// This looks for your AWS CLI credentials on your laptop
				.credentialsProvider(ProfileCredentialsProvider.create())
				.build();
	}

	@Bean
	public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
		return DynamoDbEnhancedClient.builder()
				.dynamoDbClient(dynamoDbClient)
				.build();
	}
}
