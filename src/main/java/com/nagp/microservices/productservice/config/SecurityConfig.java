//package com.nagp.microservices.productservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//				// 1. Enable CORS so React isn't blocked
//				.cors(Customizer.withDefaults())
//
//				// 2. Disable CSRF (Standard practice for stateless REST APIs)
//				.csrf(AbstractHttpConfigurer::disable)
//
//				// 3. Define URL Rules
//				.authorizeHttpRequests(auth -> auth
//						// Allow anyone to view products
//						.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
//
//						// Require authentication for POSTing new products
//						.requestMatchers(HttpMethod.POST, "/api/products/**").authenticated()
//
//						// Anything else requires authentication just to be safe
//						.anyRequest().authenticated()
//				)
//
//				// 4. Tell Spring to validate JWTs in the Authorization header
//				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//
//		return http.build();
//	}
//}