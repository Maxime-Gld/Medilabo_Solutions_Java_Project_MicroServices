package com.medilabo.solutions.notes.dto.projection;

public interface NotesWithPatientNameProjection {
    int getPatId();
    String getPatientName();
    String getNote();
}
