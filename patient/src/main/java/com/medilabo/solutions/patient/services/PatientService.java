package com.medilabo.solutions.patient.services;

import java.util.List;
import java.util.Optional;

import com.medilabo.solutions.patient.entities.PatientEntity;

public interface PatientService {
    
    void addPatient(PatientEntity patient);

    Optional<PatientEntity> getPatientById(Integer id);

    void updatePatient(PatientEntity patient);

    void deletePatient(Integer id);

    List<PatientEntity> getAllPatients();
}
