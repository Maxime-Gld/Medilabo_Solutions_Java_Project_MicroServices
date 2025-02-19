package com.medilabo.solutions.notes.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "notes")
public class NoteEntity {
    
    @Id
    private String id;
    private int patId;
    private String patient;
    private String note;
}
