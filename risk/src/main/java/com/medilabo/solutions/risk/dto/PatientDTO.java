package com.medilabo.solutions.risk.dto;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private Integer id;
    private LocalDate birthdate;
    private String gender;
}
