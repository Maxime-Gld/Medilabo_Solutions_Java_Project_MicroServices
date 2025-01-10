package com.medilabo.solutions.patient.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.medilabo.solutions.patient.entities.PatientEntity;
import com.medilabo.solutions.patient.repositories.PatientRepository;
import com.medilabo.solutions.patient.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
    
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    //Create
    @Override
    public void addPatient(PatientEntity patient) {
        patientRepository.save(patient);
    }

    //Read
    @Override
    public Optional<PatientEntity> getPatientById(Integer id) {
        return patientRepository.findById(id);
    }

    @Override
    public Optional<PatientEntity> getPatientByNameAndLastname(String name, String lastname) {
        return patientRepository.findByNameAndLastname(name, lastname);
    }

    @Override
    public void updatePatient(PatientEntity patient) {
        patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Integer id) {
        patientRepository.deleteById(id);
    }

    @Override
    public List<PatientEntity> getAllPatients() {
        return patientRepository.findAll();
    }
}
