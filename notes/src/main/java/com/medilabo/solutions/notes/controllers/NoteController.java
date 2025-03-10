package com.medilabo.solutions.notes.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/all")
    public List<NoteEntity> getAllNotes() {
        return noteService.getAllNotes();
    }
    
    @GetMapping("/{patId}")
    public List<NoteEntity> getNotesByPatId(@PathVariable int patId) {
        System.out.println("patId: " + patId);
        return noteService.getNotesByPatId(patId);
    }

    @PostMapping("/add")
    public void addNote(@RequestBody NoteEntity note) {
        System.out.println("note: " + note);
        noteService.addNote(note);
    }

    @DeleteMapping("/{patId}")
    public void deleteNoteByPatId(@PathVariable int patId) {
        noteService.deleteNoteByPatId(patId); 
    }
}
