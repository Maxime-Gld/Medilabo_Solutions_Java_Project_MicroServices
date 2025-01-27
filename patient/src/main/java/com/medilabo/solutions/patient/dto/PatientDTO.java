package com.medilabo.solutions.patient.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotNull(message = "birthdate is required")
    private LocalDate birthdate;
    @NotNull(message = "gender is required")
    @Pattern(regexp = "M|F")
    private String gender;
    private String adress;
    private String phone;
}
