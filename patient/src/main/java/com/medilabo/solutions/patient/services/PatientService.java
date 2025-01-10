package com.medilabo.solutions.patient.services;

import java.util.List;
import java.util.Optional;

import com.medilabo.solutions.patient.entities.PatientEntity;

public interface PatientService {
    
    //Create
    void addPatient(PatientEntity patient);

    //Read
    Optional<PatientEntity> getPatientById(Integer id);
    
    Optional<PatientEntity> getPatientByNameAndLastname(String name, String lastname);
    
    //Read all
    List<PatientEntity> getAllPatients();

    //Update
    void updatePatient(PatientEntity patient);

    //Delete
    void deletePatient(Integer id);
}
