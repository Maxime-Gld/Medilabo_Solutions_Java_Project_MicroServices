package com.medilabo.solutions.patient.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HmacAuthFilter extends OncePerRequestFilter {

    private final String key = "medilabo";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String hmac = request.getHeader("hmac");
        String message = request.getHeader("message");

        if (hmac == null || message == null) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Missing HMAC or message");
            return;
        }

        if (!verifyHmac(message, hmac)) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid HMAC");
            return;
        }

        filterChain.doFilter(request, response);
    }

    // Utiliser un hmac sha256
    private boolean verifyHmac(String message, String hmac) {
        String calculatedHmac = generateHmac(message, key);
        return hmac.equals(calculatedHmac);
    }

    private String generateHmac(String message, String key) {
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


    // Méthode pour renvoyer une réponse d'erreur avec un message personnalisé
    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String errorMessage) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String jsonResponse = String.format("{\"error\": \"%s\", \"status\": %d}", errorMessage, status.value());
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
