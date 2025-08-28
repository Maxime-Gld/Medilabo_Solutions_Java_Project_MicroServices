package com.medilabo.solutions.patient.dto.projection;

import java.time.LocalDate;

public record RiskPatientProjection(
    int id,
    LocalDate birthdate,
    String gender
) {} 
