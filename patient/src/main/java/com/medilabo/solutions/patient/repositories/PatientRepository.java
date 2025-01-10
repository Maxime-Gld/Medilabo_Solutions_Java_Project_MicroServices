package com.medilabo.solutions.patient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.solutions.patient.entities.PatientEntity;

@Repository
public interface PatientRepository  extends JpaRepository<PatientEntity, Integer> {

}
