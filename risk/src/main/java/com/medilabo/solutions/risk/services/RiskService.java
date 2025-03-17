package com.medilabo.solutions.risk.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.solutions.risk.client.NoteClient;
import com.medilabo.solutions.risk.client.PatientClient;

@Service
public class RiskService {

    private PatientClient patientClient;
    private NoteClient noteClient;
    private List<String> terminologie;

    public RiskService(PatientClient patientClient, NoteClient noteClient) {
        this.patientClient = patientClient;
        this.noteClient = noteClient;
        this.terminologie = List.of("Hémoglobine A1C",
                "Microalbumine",
                "Taille",
                "Poids",
                "Fumeur",
                "Fumeuse",
                "Anormal",
                "Cholestérol",
                "Vertiges",
                "Rechute",
                "Réaction",
                "Anticorps");
    }

    public String evaluateRisk(String patientId) throws Exception {
        PatientDTO patient = patientClient.getPatient(Integer.parseInt(patientId));
        List<String> notes = noteClient.getNotes(Integer.parseInt(patientId));

        return analyseRisk(patient, notes);
    }

    private String analyseRisk(String patient, List<String> notes) {

        return "Risk";
    }

    private boolean isPatientRiskNone(List<String> notes) {
        return !notes.stream().anyMatch(terminologie::contains);
    }

    private boolean isPatientRiskBorderline(String patient, List<String> notes) {
        // notes contient entre deux et cinq terminologies et le patient est âgé de plus
        // de 30 ans
        if (patient.getPatientAge() > 30) {
            return notes.stream().filter(terminologie::contains).count() >= 2
                    && notes.stream().filter(terminologie::contains).count() <= 5;
        }
        return false;
    }

    private boolean isPatientRiskInDanger(String patient, List<String> notes) {
        /* Dépend de l'âge et du sexe du patient. Si le patient est un homme
        de moins de 30 ans, alors trois termes déclencheurs doivent être présents. Si le patient
        est une femme et a moins de 30 ans, il faudra quatre termes déclencheurs. Si le patient
        a plus de 30 ans, alors il en faudra six ou sept */
        if (patient.getGender() == "M" && patient.getPatientAge() < 30) {
            return notes.stream().filter(terminologie::contains).count() >= 3;
        } else if (patient.getGender() == "F" && patient.getPatientAge() < 30) {
            return notes.stream().filter(terminologie::contains).count() >= 4;
        } else (patient.getPatientAge() > 30) {
            return notes.stream().filter(terminologie::contains).count() >= 6 && notes.stream().filter(terminologie::contains).count() <= 7;
        }
        return false;
    }

    private boolean isPatientRiskEarlyOnset(String patient, List<String> notes) {
        /*
         * Encore une fois, cela dépend de l'âge et du sexe. Si
         * le patient est un homme de moins de 30 ans, alors au moins cinq termes
         * déclencheurs
         * sont nécessaires. Si le patient est une femme et a moins de 30 ans, il faudra
         * au moins
         * sept termes déclencheurs. Si le patient a plus de 30 ans, alors il en faudra
         * huit ou plus.
         */
        if (patient.getGender() == "M" && patient.getPatientAge() < 30) {
            return notes.stream().filter(terminologie::contains).count() >= 5;
        } else if (patient.getGender() == "F" && patient.getPatientAge() < 30) {
            return notes.stream().filter(terminologie::contains).count() >= 7;
        } else (patient.getPatientAge() > 30) {
            return notes.stream().filter(terminologie::contains).count() >= 8;
        }
        return false;
    }
}
