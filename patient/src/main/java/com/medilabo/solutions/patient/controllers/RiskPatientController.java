package com.medilabo.solutions.patient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.solutions.patient.dto.RiskPatientDTO;
import com.medilabo.solutions.patient.dto.projection.RiskPatientProjection;
import com.medilabo.solutions.patient.services.PatientService;

@RestController
@RequestMapping("/patient/risk")
public class RiskPatientController {

    private PatientService patientService;

    @Autowired
    public RiskPatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskPatientDTO> getRisk(@PathVariable Integer id, RiskPatientProjection riskPatientDTO) throws IllegalArgumentException, Exception {
        return patientService.getRiskPatientDTOById(id)
                .map(patient -> new ResponseEntity<>(patient, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
