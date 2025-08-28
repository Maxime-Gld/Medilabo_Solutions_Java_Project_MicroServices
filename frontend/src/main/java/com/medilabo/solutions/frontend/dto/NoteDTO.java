package com.medilabo.solutions.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    
    private String id;
    private int patId;
    private String patientName;
    private String note;
}
