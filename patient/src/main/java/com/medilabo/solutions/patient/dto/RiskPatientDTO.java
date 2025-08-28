package com.medilabo.solutions.patient.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskPatientDTO {
    private Integer id;
    private LocalDate birthdate;
    private String gender;
}
