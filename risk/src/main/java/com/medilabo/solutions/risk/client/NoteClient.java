package com.medilabo.solutions.risk.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

@Service
public class NoteClient {
    
    private final String GATEWAY_URL = "http://gatewayService:8881/";
    private final String BACKEND_NOTE_URL = GATEWAY_URL + "note/";
    private final HttpClient httpClient;

    public NoteClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String getNotes(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BACKEND_NOTE_URL + id))
                .GET()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
