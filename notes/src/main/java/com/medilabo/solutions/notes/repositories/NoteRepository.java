package com.medilabo.solutions.notes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.solutions.notes.entities.NoteEntity;

@Repository
public interface NoteRepository extends MongoRepository<NoteEntity, String> {
    
    List<NoteEntity> findByPatId(int patId);
    void deleteByPatId(int patId);
    boolean existsByPatIdAndNote(int patId, String note);
}
