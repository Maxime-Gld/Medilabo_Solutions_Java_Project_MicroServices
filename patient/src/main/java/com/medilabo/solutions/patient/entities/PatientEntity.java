package com.medilabo.solutions.patient.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull(message = "lastname is required")
    String lastname;

    @NotNull(message = "name is required")
    String name;

    @NotNull(message = "birthday is required")
    LocalDate birthdate;

    @NotNull(message = "gender is required")
    String gender;

    @NotNull(message = "adress is required")
    String adress;

    @NotNull(message = "phone is required")
    String phone;
}
