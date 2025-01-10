package com.medilabo.solutions.patient.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.solutions.patient.entities.PatientEntity;
import com.medilabo.solutions.patient.services.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {
    // injection de la couche service
    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addPatient(@Valid PatientEntity patient, BindingResult result) {
        if (result.hasErrors()) {
            // Si des erreurs existent, retourne les erreurs de validation en réponse
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        // Ajout du patient
        patientService.addPatient(patient);

        // Retourne une réponse avec les données du patient ajouté
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientEntity>> getAllPatients() {
        List<PatientEntity> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientEntity> getPatient(@PathVariable Integer id) {
        Optional<PatientEntity> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            return new ResponseEntity<>(patient.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePatient(@PathVariable Integer id, @Valid PatientEntity patient,
            BindingResult result) {
        if (result.hasErrors()) {
            // Si des erreurs existent, retourne les erreurs de validation en réponse
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Optional<PatientEntity> patientOptional = patientService.getPatientById(id);
        if (patientOptional.isPresent()) {
            PatientEntity existingPatient = patientOptional.get();
            existingPatient.setLastname(patient.getLastname());
            existingPatient.setName(patient.getName());
            existingPatient.setBirthdate(patient.getBirthdate());
            existingPatient.setGender(patient.getGender());
            existingPatient.setAdress(patient.getAdress());
            existingPatient.setPhone(patient.getPhone());
            patientService.updatePatient(existingPatient);
            return new ResponseEntity<>(existingPatient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePatient(@PathVariable Integer id) {
        Optional<PatientEntity> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            patientService.deletePatient(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}