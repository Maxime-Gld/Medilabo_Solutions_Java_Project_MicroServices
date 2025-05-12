package com.medilabo.solutions.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteWithPatientNameDTO {
    private int patId;
    private String patient;
    private String note;
}