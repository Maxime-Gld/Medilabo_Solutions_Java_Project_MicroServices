package com.medilabo.solutions.notes.services;

import java.util.List;

import com.medilabo.solutions.notes.entities.NoteEntity;
import com.medilabo.solutions.notes.repositories.NoteRepository;

public class NoteService {
    private final NoteRepository noteRepository;
    
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    
    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll();
    }
    
    public List<NoteEntity> getNotesByPatId(int patId) {
        return noteRepository.findByPatId(patId);
    }
    
    public List<NoteEntity> getNotesByPatient(String patient) {
        return noteRepository.findByPatient(patient);
    }
}
