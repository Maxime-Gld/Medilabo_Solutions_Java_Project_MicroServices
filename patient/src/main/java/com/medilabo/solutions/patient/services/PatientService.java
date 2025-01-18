package com.medilabo.solutions.patient.services;

import java.util.List;
import java.util.Optional;

import com.medilabo.solutions.patient.dto.PatientDTO;
import com.medilabo.solutions.patient.entities.PatientEntity;

public interface PatientService {
    
    //Create
    void addPatient(PatientEntity patient);

    void addPatientDTO(PatientDTO patient);

    //Read
    Optional<PatientEntity> getPatientById(Integer id);
    
    Optional<PatientEntity> getPatientByNameAndLastname(String name, String lastname);
    
    Optional<PatientDTO> getPatientDTOById(Integer id);
    
    Optional<PatientDTO> getPatientDTOByNameAndLastname(String name, String lastname);
    
    //Read all
    List<PatientEntity> getAllPatients();

    List<PatientDTO> getAllPatientDTOs();

    //Update
    void updatePatient(PatientEntity patient);

    //Delete
    void deletePatient(Integer id);
}
