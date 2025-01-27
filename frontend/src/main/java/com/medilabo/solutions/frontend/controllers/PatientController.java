package com.medilabo.solutions.frontend.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilabo.solutions.frontend.dto.PatientDTO;

@Controller
@RequestMapping("/patient")
public class PatientController {
    
    private String urlBackend = "http://localhost:8881/patient/";
    @GetMapping("/all")
    public String patients(Model model) {

        PatientDTO[] patientsArray = restTemplate.getForObject(urlBackend+"all", PatientDTO[].class);
        List<PatientDTO> patients = List.of(patientsArray);
        model.addAttribute("patients", patients);

        return "patients";
    }

    @GetMapping("/add")
    public String addPatient(Model model) {
        return "addPatient";
    }

    @GetMapping("/update/{id}")
    public String updatePatient(Model model, Integer id) {
        // Effectuer une requete GET vers l'API pour obtenir un patient
        PatientDTO patient = restTemplate.getForObject(urlBackend+"update/"+id, PatientDTO.class);
        model.addAttribute("patient", patient);
        
        return "updatePatient";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePatient(Model model, Integer id) {
        // Effectuer une requete DELETE vers l'API pour supprimer un patient
        restTemplate.delete(urlBackend+"delete/"+id);
        
        return "redirect:/patient/all";
    }



    @PostMapping("/add")
    public String addPatient(Model model, PatientDTO patient) {
        // Effectuer une requete POST vers l'API pour ajouter un patient
        System.out.println("patient: " + patient);
        restTemplate.postForEntity(urlBackend+"add", patient, PatientDTO.class);
        
        return "redirect:/patient/all";
    }





}
