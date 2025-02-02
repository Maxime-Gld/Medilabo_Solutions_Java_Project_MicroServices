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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.solutions.patient.dto.ApiResponseDTO;
import com.medilabo.solutions.patient.dto.PatientDTO;
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
    public ResponseEntity<ApiResponseDTO<PatientDTO>> addPatient(@Valid @RequestBody PatientDTO patientDTO, BindingResult result) {
        if (result.hasErrors()) {
            // Si des erreurs existent, retourne les erreurs de validation en réponse
            List<String> errors = result.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList();
            return new ResponseEntity<>(new ApiResponseDTO<>(errors), HttpStatus.BAD_REQUEST);
        }

        // Ajout du patient
        patientService.addPatientDTO(patientDTO);

        // Retourne une réponse avec les données du patient ajouté
        return new ResponseEntity<>(new ApiResponseDTO<>(patientDTO), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patientDTOs = patientService.getAllPatientDTOs();

        return new ResponseEntity<>(patientDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Integer id) {
        return patientService.getPatientDTOById(id)
            .map(patient -> new ResponseEntity<>(patient, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO<PatientDTO>> updatePatient(@PathVariable Integer id, @Valid @RequestBody PatientDTO patient,
            BindingResult result) { 
        if (result.hasErrors()) {
            // Si des erreurs existent, retourne les erreurs de validation en réponse
            List<String> errors = result.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList();
            return new ResponseEntity<>(new ApiResponseDTO<>(errors), HttpStatus.BAD_REQUEST);
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

            // Convertir PatientEntity en PatientDTO
            PatientDTO patientDTO = new PatientDTO(existingPatient.getId(), existingPatient.getName(),
                    existingPatient.getLastname(), existingPatient.getBirthdate(), existingPatient.getGender(),
                    existingPatient.getAdress(), existingPatient.getPhone());

            return new ResponseEntity<>(new ApiResponseDTO<>(patientDTO), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable Integer id) {
        Optional<PatientEntity> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            patientService.deletePatient(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}