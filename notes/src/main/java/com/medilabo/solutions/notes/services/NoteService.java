package com.medilabo.solutions.notes.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.solutions.notes.dto.NoteWithPatientNameDTO;
import com.medilabo.solutions.notes.entities.NoteEntity;
import com.medilabo.solutions.notes.entities.PatientEntity;
import com.medilabo.solutions.notes.repositories.NoteRepository;
import com.medilabo.solutions.notes.repositories.PatientRepository;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final PatientRepository patientRepository;

    public NoteService(NoteRepository noteRepository, PatientRepository patientRepository) {
        this.noteRepository = noteRepository;
        this.patientRepository = patientRepository;
    }

    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll();
    }

    public List<NoteWithPatientNameDTO> getNotesByPatId(int patId) {
        List<NoteEntity> notes = noteRepository.findByPatId(patId);
        PatientEntity patient = patientRepository.findByPatId(patId);
        return notes.stream()
                .map(note -> new NoteWithPatientNameDTO(
                        note.getPatId(),
                        patient.getName(),
                        note.getNote()))
                .toList();

    }

    public void addNote(NoteWithPatientNameDTO note) {
        // Vérifier si le patient existe déjà
        if (!patientRepository.existsByPatId(note.getPatId())) {
            // Créer le patient si non existant
            PatientEntity newPatient = new PatientEntity();
            newPatient.setPatId(note.getPatId());
            newPatient.setName(note.getPatient());
            patientRepository.save(newPatient);
        }

        // Vérifier si la même note existe déjà pour ce patient (basée sur patId et
        // contenu de note)
        boolean noteExists = noteRepository.existsByPatIdAndNote(note.getPatId(), note.getNote());
        if (noteExists) {
            throw new IllegalArgumentException("Cette note a déjà été ajoutée pour ce patient.");
        }

        // Ajouter la note
        NoteEntity newNote = new NoteEntity();
        newNote.setPatId(note.getPatId());
        newNote.setNote(note.getNote());
        noteRepository.save(newNote);
    }

    public void deleteNoteByPatId(int patId) {
        noteRepository.deleteByPatId(patId);
    }
}
