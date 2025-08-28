package com.medilabo.solutions.notes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.solutions.notes.dto.NoteDTO;
import com.medilabo.solutions.notes.services.NoteService;

@RestController
@RequestMapping("/notes")
public class NoteRiskController {

    private NoteService noteService;

    @Autowired
    public NoteRiskController(NoteService noteService) {
        this.noteService = noteService;
    }

        @GetMapping("/risk/{patId}")
    public List<NoteDTO> getNotesOnlyByPatId(@PathVariable int patId) {
        return noteService.getNotesOnlyByPatId(patId);
    }
    
}
