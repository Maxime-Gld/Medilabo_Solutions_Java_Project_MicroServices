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
import com.medilabo.solutions.frontend.dto.NoteDTO;
import com.medilabo.solutions.frontend.dto.PatientDTO;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private Hmac hmacService = new Hmac();
    private static final String GATEWAY_URL = "http://gatewayService:8881/";
    private static final String SECRET_KEY = "medilabo";

    @GetMapping("/{id}")
    public String getPatientNote(Model model, @PathVariable int id) throws IOException, InterruptedException {

        // générer un hmac pour communiquer avec l'API
        String randomString = hmacService.generateRandomString();
        String hmac = hmacService.generateHmac(randomString, SECRET_KEY);

        // Effectuer une requete httClient GET vers l'API pour obtenir les notes du
        // patient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GATEWAY_URL + "notes/" + id))
                .header("hmac", hmac)
                .header("message", randomString)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<NoteDTO> noteList = objectMapper.readValue(response.body(), new TypeReference<List<NoteDTO>>() {
            });

            if (noteList.isEmpty()) {
                model.addAttribute("listNote", null);
                // request sur l'api patient pour récuperer le patient et l'ajouter au model
                HttpRequest requestPatient = HttpRequest.newBuilder()
                        .uri(URI.create(GATEWAY_URL + "patient/" + id))
                        .header("hmac", hmac)
                        .header("message", randomString)
                        .GET()
                        .build();

                HttpResponse<String> responsePatient = httpClient.send(requestPatient,
                        HttpResponse.BodyHandlers.ofString());

                if (responsePatient.statusCode() == 200) {
                    PatientDTO patient = objectMapper.readValue(responsePatient.body(), PatientDTO.class);
                    NoteDTO note = new NoteDTO();
                    note.setPatId(id);
                    note.setPatient(patient.getLastname());
                    model.addAttribute("note", note);
                }
                return "addNote";
            }

            model.addAttribute("listNote", noteList);
            return "addNote";
        }

        return "addNote";
    }

    @PostMapping("/add")
    public String addPatientNote(Model model, NoteDTO noteDto) throws IOException, InterruptedException {
        System.out.println("noteDto: " + noteDto);
        String randomString = hmacService.generateRandomString();
        String hmac = hmacService.generateHmac(randomString, SECRET_KEY);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String noteDtoJson = objectMapper.writeValueAsString(noteDto);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GATEWAY_URL + "notes/" + "add"))
                .header("Content-Type", "application/json")
                .header("hmac", hmac)
                .header("message", randomString)
                .POST(HttpRequest.BodyPublishers.ofString(noteDtoJson))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return "redirect:/notes/" + noteDto.getPatId();
        }
        model.addAttribute("error", "Une erreur s'est produite lors de l'ajout de la note.");
        return "redirect:/notes/" + noteDto.getPatId();
    }

}
