package com.medilabo.solutions.risk.services;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.solutions.risk.client.NoteClient;
import com.medilabo.solutions.risk.client.PatientClient;
import com.medilabo.solutions.risk.dto.NoteDTO;
import com.medilabo.solutions.risk.dto.PatientDTO;
import com.medilabo.solutions.risk.enums.RiskConstant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RiskServiceTest {

    @Mock
    private PatientClient patientClient;

    @Mock
    private NoteClient noteClient;

    @InjectMocks
    private RiskService riskService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, Object> jsonData;

    @BeforeEach
    void setUp() throws IOException, StreamReadException, DatabindException {
        MockitoAnnotations.openMocks(this);
        jsonData = objectMapper.readValue(
                new File("src/test/resources/patients_notes.json"),
                new TypeReference<Map<String, Object>>() {
                });
    }

    private PatientDTO getPatient(int id) {
        List<Map<String, Object>> patients = objectMapper.convertValue(
                jsonData.get("patients"), new TypeReference<List<Map<String, Object>>>() {
                });

        return patients.stream()
                .filter(p -> (int) p.get("id") == id)
                .findFirst()
                .map(patient -> new PatientDTO(id,
                        (String) patient.get("name"),
                        (String) patient.get("lastname"),
                        LocalDate.parse((String) patient.get("birthdate")),
                        (String) patient.get("gender"),
                        null, null))
                .orElseThrow(() -> new IllegalArgumentException("Patient avec ID " + id + " non trouvé"));
    }

    private List<NoteDTO> getNotes(int patientId) {
        List<Map<String, Object>> notesList = objectMapper.convertValue(jsonData.get("notes"),
                new TypeReference<List<Map<String, Object>>>() {
                });

        // Trouver l'élément correspondant au patientId
        List<NoteDTO> notes = notesList.stream()
                .filter(note -> (int) note.get("patId") == patientId) // Filtrage par patientId
                .map(note -> // Pour chaque entrée, nous allons créer une NoteDTO
                new NoteDTO(
                        (String) note.get("Id"), // Récupérer l'Id
                        (int) note.get("patId"), // Récupérer patId
                        (String) note.get("patient"), // Récupérer le nom du patient
                        (String) note.get("note") // Récupérer la note
                ))
                .collect(Collectors.toList()); // Collecter toutes les NoteDTO dans une liste
                
        return notes;
    }

    @Test
    void testEvaluateRiskNone() throws Exception {
        PatientDTO patientDTO = getPatient(1);
        List<NoteDTO> noteDTOList = getNotes(1);

        when(patientClient.getPatient(1)).thenReturn(patientDTO);
        when(noteClient.getNotes(1)).thenReturn(noteDTOList);

        List<String> notes = noteDTOList.stream().map(NoteDTO::getNote).collect(Collectors.toList());
        RiskConstant risk = riskService.analyseRisk(patientDTO, notes);

        assertEquals(RiskConstant.NONE, risk);
    }

    @Test
    void testEvaluateRiskBorderline() throws Exception {
        PatientDTO patientDTO = getPatient(2);
        List<NoteDTO> noteDTOList = getNotes(2);

        when(patientClient.getPatient(2)).thenReturn(patientDTO);
        when(noteClient.getNotes(2)).thenReturn(noteDTOList);

        List<String> notes = noteDTOList.stream().map(NoteDTO::getNote).collect(Collectors.toList());
        RiskConstant risk = riskService.analyseRisk(patientDTO, notes);

        assertEquals(RiskConstant.BORDERLINE, risk);
    }

    @Test
    void testEvaluateRiskInDanger() throws Exception {
        PatientDTO patientDTO = getPatient(3);
        List<NoteDTO> noteDTOList = getNotes(3);

        when(patientClient.getPatient(3)).thenReturn(patientDTO);
        when(noteClient.getNotes(3)).thenReturn(noteDTOList);

        List<String> notes = noteDTOList.stream().map(NoteDTO::getNote).collect(Collectors.toList());
        RiskConstant risk = riskService.analyseRisk(patientDTO, notes);

        assertEquals(RiskConstant.IN_DANGER, risk);
    }

    @Test
    void testEvaluateRiskEarlyOnset() throws Exception {
        PatientDTO patientDTO = getPatient(4);
        List<NoteDTO> noteDTOList = getNotes(4);

        when(patientClient.getPatient(4)).thenReturn(patientDTO);
        when(noteClient.getNotes(4)).thenReturn(noteDTOList);

        List<String> notes = noteDTOList.stream().map(NoteDTO::getNote).collect(Collectors.toList());
        RiskConstant risk = riskService.analyseRisk(patientDTO, notes);

        assertEquals(RiskConstant.EARLY_ONSET, risk);
    }
}
