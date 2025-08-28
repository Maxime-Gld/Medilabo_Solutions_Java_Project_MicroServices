package com.medilabo.solutions.patient.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medilabo.solutions.patient.dto.projection.RiskPatientProjection;
import com.medilabo.solutions.patient.entities.PatientEntity;

@Repository
public interface PatientRepository  extends JpaRepository<PatientEntity, Integer> {

    Optional<PatientEntity> findByNameAndLastname(String name, String lastname);

    // utilisation de @Query pour sélectionner uniquement les champs nécessaires pour la projection
    @Query("SELECT new com.medilabo.solutions.patient.dto.projection.RiskPatientProjection(p.id, p.birthdate, p.gender) FROM PatientEntity p WHERE p.id = :id")
    Optional<RiskPatientProjection> findRiskPatientById(@Param("id") int id);
}
