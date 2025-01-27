package com.medilabo.solutions.frontend.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.medilabo.solutions.frontend.dto.ApiResponseDTO;
import com.medilabo.solutions.frontend.dto.PatientDTO;

@Service
public class PatientService {

    private final RestClient restClient;

    public PatientService(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<ApiResponseDTO<PatientDTO>> addPatientDTO(PatientDTO patientDTO) {
        if (patientDTO == null) {
            throw new IllegalArgumentException("PatientDTO cannot be null");
        }

        return restClient.post()
                .uri("/patient/add")
                .body(patientDTO)
                .retrieve()
                .body(ApiResponseDTO.class);
    }

}
