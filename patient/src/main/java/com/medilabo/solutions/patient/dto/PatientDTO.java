package com.medilabo.solutions.patient.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    
    private Integer id;
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "lastname is required")
    private String lastname;
    @NotNull(message = "birthday is required")
    private LocalDate birthdate;
    @NotNull(message = "gender is required")
    private String gender;
    private String adress;
    private String phone;
}
