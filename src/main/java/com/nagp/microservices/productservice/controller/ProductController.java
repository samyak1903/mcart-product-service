package com.nagp.microservices.productservice.controller;

import com.nagp.microservices.productservice.dto.StrapiWebhookDTO;
import com.nagp.microservices.productservice.entity.Product;
import com.nagp.microservices.productservice.repository.ProductRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/products")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://d2emlgpiz49p5b.cloudfront.net")
public class ProductController {

	private final ProductRepository productRepository;

	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@GetMapping
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	// GET endpoint for the PDP (Product Detail Page)
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable String id) {
		Product product = productRepository.findById(id);
		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// POST endpoint for Admins/CMS to add new products
	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		if (product.getId() == null || product.getId().isEmpty()) {
			product.setId(UUID.randomUUID().toString()); // Auto-generate ID if missing
		}
		productRepository.save(product);
		return product;
	}

	@PostMapping("/webhook/strapi")
	public ResponseEntity<String> handleStrapiWebhook(@RequestBody StrapiWebhookDTO payload) {

		// 1. Safety check: Only process "product" webhooks
		if (!"product".equals(payload.getModel())) {
			return ResponseEntity.ok("Ignored non-product webhook");
		}

		StrapiWebhookDTO.StrapiEntry entry = payload.getEntry();

		// 2. Map the DTO to your actual DynamoDB Entity
		Product product = new Product();

		// Strapi uses numeric IDs, but your DynamoDB uses Strings.
		// We generate a fresh UUID for the database partition key.
		product.setId(UUID.randomUUID().toString());

		product.setName(entry.getName());
		product.setDescription(entry.getDescription());
		product.setPrice(entry.getPrice());
		product.setStockQuantity(entry.getStockQuantity());
		product.setCategory(entry.getCategory());

		// Safely extract the S3 URL if an image was uploaded
		if (entry.getImage() != null && entry.getImage().getUrl() != null) {
			product.setImageUrl(entry.getImage().getUrl());
		}

		// 3. Save to DynamoDB!
		productRepository.save(product);

		return ResponseEntity.ok("Product successfully synced to DynamoDB!");
	}
}