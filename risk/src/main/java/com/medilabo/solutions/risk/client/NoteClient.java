package com.medilabo.solutions.risk.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabo.solutions.risk.config.Hmac;
import com.medilabo.solutions.risk.dto.NoteDTO;

@Service
public class NoteClient {
    
    private static final String SECRET_KEY = "medilabo";
    private final String GATEWAY_URL = "http://gatewayService:8881/";
    private final String BACKEND_NOTE_URL = GATEWAY_URL + "notes/";
    private final HttpClient httpClient;
    private final Hmac hmacService = new Hmac();

    public NoteClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public List<NoteDTO> getNotes(int id) throws IOException, InterruptedException {

        // générer un hmac pour communiquer avec l'API
        String randomString = hmacService.generateRandomString();
        String hmac = hmacService.generateHmac(randomString, SECRET_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BACKEND_NOTE_URL + id))
                .header("hmac", hmac)
                .header("message", randomString)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(response.body(), new TypeReference<List<NoteDTO>>() {});
        }
        return null;
    }
}
