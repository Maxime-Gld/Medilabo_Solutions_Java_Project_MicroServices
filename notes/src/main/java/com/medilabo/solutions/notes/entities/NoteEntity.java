package com.medilabo.solutions.notes.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
@CompoundIndex(name = "patient_note_unique_index", def = "{'patId': 1, 'note': 1}", unique = true)
public class NoteEntity {
    
    @Id
    private String id;

    @Indexed
    private int patId;
    private String patientName;
    private String note;
    private LocalDateTime createdAt = LocalDateTime.now();
}
