package com.medilabo.solutions.patient.services;

import java.util.List;
import java.util.Optional;

import com.medilabo.solutions.patient.dto.PatientDTO;
import com.medilabo.solutions.patient.dto.RiskPatientDTO;
import com.medilabo.solutions.patient.entities.PatientEntity;

public interface PatientService {
    
    //Create
    void addPatient(PatientEntity patient);

    void addPatientDTO(PatientDTO patient);

    //Read
    Optional<PatientEntity> getPatientById(Integer id);
    
    Optional<PatientDTO> getPatientDTOById(Integer id);

    Optional<RiskPatientDTO> getRiskPatientDTOById(Integer id);
    
    //Read all
    List<PatientDTO> getAllPatientDTOs();

    //Update
    void updatePatient(PatientEntity patient);

    //Delete
    void deletePatient(Integer id);
}
