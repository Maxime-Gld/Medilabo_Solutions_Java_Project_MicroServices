package com.medilabo.solutions.notes.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.medilabo.solutions.notes.entities.PatientEntity;

public interface PatientRepository extends MongoRepository<PatientEntity, String> {

    PatientEntity findByName(String name);
    PatientEntity findByPatId(int patId);
    boolean existsByPatId(int patId);
}
