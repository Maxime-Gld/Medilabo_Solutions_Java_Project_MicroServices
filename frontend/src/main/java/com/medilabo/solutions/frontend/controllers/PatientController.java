package com.medilabo.solutions.frontend.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabo.solutions.frontend.config.Hmac;
import com.medilabo.solutions.frontend.dto.PatientDTO;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private Hmac hmacService = new Hmac();
    private static final String GATEWAY_URL = "http://gatewayService:8881/";
    private static final String SECRET_KEY = "medilabo";

    @GetMapping("/all")
    public String patients(Model model) throws IOException, InterruptedException {
        // générer un hmac pour communiquer avec l'API
        String randomString = hmacService.generateRandomString();
        String hmac = hmacService.generateHmac(randomString, SECRET_KEY);

        // Effectuer une requete HttpClient GET vers l'API pour obtenir la liste des
        // patients
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GATEWAY_URL + "patient/all"))
                .header("hmac", hmac)
                .header("message", randomString)
                .GET()
                .build();

        // récupérer la liste des patients
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Vérifier si la requête est réussie
        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            // Convertir la réponse JSON en liste de PatientDTO
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<PatientDTO> patients = objectMapper.readValue(response.body(), new TypeReference<List<PatientDTO>>() {
            });

            // Ajouter la liste des patients au modèle
            model.addAttribute("patients", patients);
        }
        return "patients";
    }

    @GetMapping("/update/{id}")
    public String updatePatient(Model model, @PathVariable Integer id) throws IOException, InterruptedException {
        // générer un hmac pour communiquer avec l'API
        String randomString = hmacService.generateRandomString();
        String hmac = hmacService.generateHmac(randomString, SECRET_KEY);
        // Effectuer une requete httClient PUT vers l'API pour obtenir un patient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GATEWAY_URL + "patient/" +id))
                .header("hmac", hmac)
                .header("message", randomString)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            PatientDTO patient = objectMapper.readValue(response.body(), PatientDTO.class);
            model.addAttribute("patient", patient);
        }
        return "updatePatient";
    }

    @PostMapping("/delete/{id}")
    public String deletePatient(Model model, @PathVariable Integer id) throws IOException, InterruptedException {
        // générer un hmac pour communiquer avec l'API
        String randomString = hmacService.generateRandomString();
        String hmac = hmacService.generateHmac(randomString, SECRET_KEY);
        // Effectuer une requete httpCient DELETE vers l'API pour supprimer un patient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GATEWAY_URL + "patient/" + id))
                .header("hmac", hmac)
                .header("message", randomString)
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create(GATEWAY_URL + "notes/"+id))
                    .header("hmac", hmac)
                    .header("message", randomString)
                    .DELETE()
                    .build();

            HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());

            if (response2.statusCode() >= 200 && response2.statusCode() <= 299) {
                return "redirect:/patient/all";
            }
        }

        return "redirect:/error/500";
    }

    @PostMapping("/add")
    public String addPatient(Model model, PatientDTO patient) throws IOException, InterruptedException {
        // générer un hmac pour communiquer avec l'API
        String randomString = hmacService.generateRandomString();
        String hmac = hmacService.generateHmac(randomString, SECRET_KEY);
        // Effectuer une requete POST vers l'API pour ajouter un patient
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(patient);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GATEWAY_URL + "patient/add"))
                .header("Content-Type", "application/json")
                .header("hmac", hmac)
                .header("message", randomString)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        

        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            return "redirect:/patient/all";
        }

        return "redirect:/error/500";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(Model model, @PathVariable Integer id, PatientDTO patient) throws IOException, InterruptedException {
        // générer un hmac pour communiquer avec l'API
        String randomString = hmacService.generateRandomString();
        String hmac = hmacService.generateHmac(randomString, SECRET_KEY);
        // Effectuer une requete httClient PUT vers l'API pour modifier un patient
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(patient);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GATEWAY_URL + "patient/update/" + id))
                .header("Content-Type", "application/json")
                .header("hmac", hmac)
                .header("message", randomString)
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            return "redirect:/patient/all";
        }
        return "error/500";
    }

}
