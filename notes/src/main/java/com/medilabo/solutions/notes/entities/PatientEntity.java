package com.medilabo.solutions.notes.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "patients")
@CompoundIndex(def = "{'patId': 1}", unique = true)
public class PatientEntity {

    @Id
    private String id;
    private int patId;
    private String name;
}
