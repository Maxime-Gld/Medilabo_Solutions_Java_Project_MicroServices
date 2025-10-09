package com.medilabo.solutions.notes.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.solutions.notes.dto.NoteDTO;
import com.medilabo.solutions.notes.dto.NoteRequestDTO;
import com.medilabo.solutions.notes.dto.projection.NotesWithPatientNameProjection;
import com.medilabo.solutions.notes.entities.NoteEntity;
import com.medilabo.solutions.notes.repositories.NoteRepository;
import com.mongodb.DuplicateKeyException;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // Récupérer toutes les notes (attention au volume) => pagination pour green code ?
    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll();
    }

    // Récupérer les notes d'un patient avec patientName déjà inclus
    public List<NotesWithPatientNameProjection> findByPatId(int patId) {
        return noteRepository.findByPatId(patId);
    }

    // Récupérer uniquement les notes sans le nom du patient (pour analyse de risque)
    public List<NoteDTO> getNotesOnlyByPatId(int patId) {
        return noteRepository.findNotesByPatId(patId)
                .stream()
                .map(proj -> new NoteDTO(
                        proj.getPatId(),
                        proj.getNote()))
                .toList();
    }

    // Ajouter une nouvelle note
    public void addNote(NoteRequestDTO noteDto) {
        try {
            NoteEntity newNote = new NoteEntity();
            newNote.setPatId(noteDto.getPatId());
            newNote.setPatientName(noteDto.getPatientName());
            newNote.setNote(noteDto.getNote());
            noteRepository.save(newNote);
        } catch (DuplicateKeyException e) {
            // MongoDB a détecté un doublon automatiquement grâce à l'index unique
            throw new IllegalArgumentException(
                "Cette note a déjà été ajoutée pour ce patient."
            );
        }
    }

    // Supprimer toutes les notes d'un patient
    public void deleteNoteByPatId(int patId) {
        noteRepository.deleteByPatId(patId);
    }
}
