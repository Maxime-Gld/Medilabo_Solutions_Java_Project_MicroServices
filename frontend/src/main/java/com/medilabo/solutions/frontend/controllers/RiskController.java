package com.medilabo.solutions.frontend.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilabo.solutions.frontend.config.Hmac;
import com.medilabo.solutions.frontend.enums.RiskConstant;

@Controller
@RequestMapping("/risk")
public class RiskController {

    private Hmac hmacService = new Hmac();
    private static final String GATEWAY_URL = "http://gatewayService:8881/";
    private static final String SECRET_KEY = "medilabo";

    @GetMapping("/{id}")
    public String getRisk(Model model, @PathVariable int id) throws IOException, InterruptedException {
        
        // générer un hmac pour communiquer avec l'API
        String randomString = hmacService.generateRandomString();
        String hmac = hmacService.generateHmac(randomString, SECRET_KEY);

        // Effectuer une requete httClient GET vers l'API pour obtenir les risques du
        // patient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GATEWAY_URL + "risk/" + id))
                .header("hmac", hmac)
                .header("message", randomString)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // la réponse est un enum de risque
        if (response.statusCode() == 200) {

            try {
                RiskConstant risk = RiskConstant.valueOf(response.body());
                model.addAttribute("risk", risk);
                model.addAttribute("message", risk.getMessage());
                return "risk";
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return "error";
            }
        } else {
            return "error";
        }
    }
    
}
