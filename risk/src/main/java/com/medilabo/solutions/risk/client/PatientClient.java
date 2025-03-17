package com.medilabo.solutions.risk.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

@Service
public class PatientClient {

    private final String GATEWAY_URL = "http://gatewayService:8881/";
    private final String BACKEND_PATIENT_URL = GATEWAY_URL + "patient/";
    private final HttpClient httpClient;

    public PatientClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String getPatient(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BACKEND_PATIENT_URL + id))
                .GET()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
    
}
