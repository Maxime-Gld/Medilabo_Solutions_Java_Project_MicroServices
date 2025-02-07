package com.medilabo.solutions.gateway.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class HmacValidationFilter extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {

            // Extraire les informations du header
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String hmac = headers.getFirst("hmac");
            String message = headers.getFirst("message");

            // Vérifier si les headers sont présents
            if (hmac == null || message == null) {
                // Rejetter la requête si le HMAC ou le message est manquant avec un statut 400
                // et une erreur
                return sendErrorResponse(exchange, HttpStatus.BAD_REQUEST, "Missing HMAC or message");
            }

            // Vérifier le HMAC
            if (!verifyHmac(message, hmac)) {
                // Rejetter la requête si le HMAC est invalide avec erreur 401
                return sendErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Invalid HMAC");
            }

            // Passer à la suite de la chaîne de filtres
            return chain.filter(exchange);
        };
    }

    private final String key = "medilabo";

    // réaliser un hmac sha256
    private String generateHmac(String message, String key) {
        System.out.println("message: " + message);
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] hmacBytes = sha256Hmac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC", e);
        }

    }

    private Boolean verifyHmac(String message, String hmac) {
        String calculatedHmac = generateHmac(message, key);
        return hmac.equals(calculatedHmac);
    }

    // Méthode pour renvoyer une réponse d'erreur avec un message personnalisé
    private Mono<Void> sendErrorResponse(ServerWebExchange exchange, HttpStatus status, String errorMessage) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Construire le message JSON
        String jsonResponse = String.format("{\"error\": \"%s\", \"status\": %d}", errorMessage, status.value());

        DataBuffer buffer = new DefaultDataBufferFactory().wrap(jsonResponse.getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
