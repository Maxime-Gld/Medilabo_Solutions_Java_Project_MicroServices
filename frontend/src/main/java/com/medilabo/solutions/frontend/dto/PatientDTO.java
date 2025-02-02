package com.medilabo.solutions.frontend.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    
    private Integer id;
    private String name;
    private String lastname;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private String gender;
    private String adress;
    private String phone;
}
