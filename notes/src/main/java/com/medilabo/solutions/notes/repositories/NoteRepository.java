package com.medilabo.solutions.notes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medilabo.solutions.notes.dto.projection.NoteProjection;
import com.medilabo.solutions.notes.dto.projection.NotesWithPatientNameProjection;
import com.medilabo.solutions.notes.entities.NoteEntity;

@Repository
public interface NoteRepository extends MongoRepository<NoteEntity, String> {

    List<NotesWithPatientNameProjection> findByPatId(int patId);

    void deleteByPatId(int patId);

    List<NoteProjection> findNotesByPatId(@Param("patId") int patId);

}
