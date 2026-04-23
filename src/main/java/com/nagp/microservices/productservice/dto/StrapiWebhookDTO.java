package com.nagp.microservices.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Extremely important! Tells Spring to ignore Strapi's extra metadata
public class StrapiWebhookDTO {

	private String event;   // e.g., "entry.create" or "entry.update"
	private String model;   // e.g., "product"
	private StrapiEntry entry;

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class StrapiEntry {
		private String name;
		private String description;
		private Double price;
		private Integer stockQuantity;
		private String category;
		private StrapiImage image;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class StrapiImage {
		private String url; // This extracts the S3 URL!
	}
}