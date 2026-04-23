package com.nagp.microservices.productservice.repository;

import java.util.List;

import com.nagp.microservices.productservice.entity.Product;

import org.springframework.stereotype.Repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;


@Repository
public class ProductRepository {

	private final DynamoDbTable<Product> productTable;

	public ProductRepository(DynamoDbEnhancedClient enhancedClient) {
		this.productTable = enhancedClient.table("products", TableSchema.fromBean(Product.class));
	}

	// Save or Update a product
	public void save(Product product) {
		productTable.putItem(product);
	}

	public List<Product> findAll() {
		return productTable.scan().items().stream().toList();
	}

	// Get a product by ID for the PDP page
	public Product findById(String id) {
		Key key = Key.builder().partitionValue(id).build();
		return productTable.getItem(key);
	}
}
