package com.medilabo.solutions.patient.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class GatewaySecurityFilter extends OncePerRequestFilter {

    private static final String SECRET_KEY = "my-secret-key";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String gatewaySecret = request.getHeader("Gateway-secret");

        if (!SECRET_KEY.equals(gatewaySecret)) {
            sendErrorResponse(response, HttpStatus.FORBIDDEN, "Access Denied");
            return;
        }

        filterChain.doFilter(request, response);
    }

    // Méthode pour renvoyer une réponse d'erreur avec un message personnalisé
    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String errorMessage)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String jsonResponse = String.format("{\"error\": \"%s\", \"status\": %d}", errorMessage, status.value());
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}