package com.medilabo.solutions.notes.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.solutions.notes.entities.NoteEntity;
import com.medilabo.solutions.notes.services.NoteService;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteEntity> getAllNotes() {
        return noteService.getAllNotes();
    }
    
    @GetMapping("/patientId/{patId}")
    public List<NoteEntity> getNotesByPatId(@PathVariable int patId) {
        return noteService.getNotesByPatId(patId);
    }
    
    @GetMapping("/patient/{patient}")
    public List<NoteEntity> getNotesByPatient(@PathVariable String patient) {
        return noteService.getNotesByPatient(patient);
    }
}
