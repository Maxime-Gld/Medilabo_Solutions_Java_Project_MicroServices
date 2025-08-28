package com.medilabo.solutions.patient.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.medilabo.solutions.patient.dto.PatientDTO;
import com.medilabo.solutions.patient.dto.RiskPatientDTO;
import com.medilabo.solutions.patient.entities.PatientEntity;
import com.medilabo.solutions.patient.repositories.PatientRepository;
import com.medilabo.solutions.patient.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Create
    @Override
    public void addPatient(PatientEntity patient) {
        patientRepository.save(patient);
    }

    @Override
    public void addPatientDTO(PatientDTO patient) {
        PatientEntity patientEntity = convertToPatientEntity(patient);
        addPatient(patientEntity);
    }

    // Read
    @Override
    public Optional<PatientEntity> getPatientById(Integer id) {
        return patientRepository.findById(id);
    }

    @Override
    public void updatePatient(PatientEntity patient) {
        patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Integer id) {
        patientRepository.deleteById(id);
    }

    // convert PatientEntity to PatientDTO
    @Override
    public Optional<PatientDTO> getPatientDTOById(Integer id) {
        return patientRepository.findById(id).map(this::convertToPatientDTO);
    }

    @Override
    public List<PatientDTO> getAllPatientDTOs() {
        List<PatientEntity> patients = patientRepository.findAll();
        return patients.stream().map(this::convertToPatientDTO).toList();
    }

    @Override
    public Optional<RiskPatientDTO> getRiskPatientDTOById(Integer id) {
        System.out.println("Fetching risk patient data for ID: " + id);
        return patientRepository.findRiskPatientById(id).map(proj -> new RiskPatientDTO(
                proj.id(),
                proj.birthdate(),
                proj.gender()));
    }

    private PatientDTO convertToPatientDTO(PatientEntity patient) {
        return new PatientDTO(patient.getId(), patient.getName(), patient.getLastname(), patient.getBirthdate(),
                patient.getGender(), patient.getAdress(), patient.getPhone());
    }

    private PatientEntity convertToPatientEntity(PatientDTO patient) {
        return new PatientEntity(patient.getId(), patient.getName(), patient.getLastname(), patient.getBirthdate(),
                patient.getGender(), patient.getAdress(), patient.getPhone());
    }
}
