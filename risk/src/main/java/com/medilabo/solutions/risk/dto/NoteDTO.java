package com.medilabo.solutions.risk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
    
    private String id;
    private int patId;
    private String patient;
    private String note;
}
