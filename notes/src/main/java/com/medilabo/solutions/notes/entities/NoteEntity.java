package com.medilabo.solutions.notes.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
public class NoteEntity {
    
    @Id
    private String id;
    private int patId;
    private String note;
    private Date createdAt = new Date();
}
