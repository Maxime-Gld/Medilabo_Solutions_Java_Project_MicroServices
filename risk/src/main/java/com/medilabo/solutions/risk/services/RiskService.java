package com.medilabo.solutions.risk.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilabo.solutions.risk.client.NoteClient;
import com.medilabo.solutions.risk.client.PatientClient;
import com.medilabo.solutions.risk.dto.NoteDTO;
import com.medilabo.solutions.risk.dto.PatientDTO;
import com.medilabo.solutions.risk.enums.RiskConstant;

@Service
public class RiskService {

    private PatientClient patientClient;
    private NoteClient noteClient;
    private List<String> terminologie = List.of(
        "Hémoglobine A1C",
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
        "Anticorps"
);

    @Autowired
    public RiskService(PatientClient patientClient, NoteClient noteClient) {
        this.patientClient = patientClient;
        this.noteClient = noteClient;
    }

    public Optional<RiskConstant> evaluateRisk(int patientId) throws Exception {
        PatientDTO patient = patientClient.getPatient(patientId);
        
        List<NoteDTO> notes = noteClient.getNotes(patientId);
        if (notes == null || notes.isEmpty() || patient == null) {
            return Optional.empty();
        }

        List<String> notesStr = notes.stream().map(NoteDTO::getNote).toList();
        return Optional.of(analyseRisk(patient, notesStr));
    }

    public RiskConstant analyseRisk(PatientDTO patient, List<String> notes) {
        long count = countDistinctOccurrences(notes);
        if (isPatientRiskEarlyOnset(patient, count)) {
            return RiskConstant.EARLY_ONSET;
        } else if (isPatientRiskInDanger(patient, count)) {
            return RiskConstant.IN_DANGER;
        } else if (isPatientRiskBorderline(patient, count)) {
            return RiskConstant.BORDERLINE;
        } else {
            return RiskConstant.NONE;
        }
        // return RiskConstant.NO_CONCLUSION;
    }

    private boolean isPatientRiskNone(long count) {
        return count == 0;
    }

    /*
     * notes contient entre deux et cinq terminologies et le patient est âgé de plus
     * de 30 ans
     */
    private boolean isPatientRiskBorderline(PatientDTO patient, long count) {
        if (isOver30yo(patient)) {
            return count >= 2 && count <= 5;
        }
        return false;
    }

    /*
     * Dépend de l'âge et du sexe du patient. Si le patient est un homme
     * de moins de 30 ans, alors trois termes déclencheurs doivent être présents. Si
     * le patient
     * est une femme et a moins de 30 ans, il faudra quatre termes déclencheurs. Si
     * le patient
     * a plus de 30 ans, alors il en faudra six ou sept
     */
    private boolean isPatientRiskInDanger(PatientDTO patient, long count) {
        if (patient.getGender().equals("M") && !isOver30yo(patient)) {
            return count >= 3;
        } else if (patient.getGender().equals("F") && !isOver30yo(patient)) {
            return count >= 4;
        } else if (isOver30yo(patient)) {
            return count >= 6 && count <= 7;
        }
        return false;
    }

    /*
     * Encore une fois, cela dépend de l'âge et du sexe. Si
     * le patient est un homme de moins de 30 ans, alors au moins cinq termes
     * déclencheurs
     * sont nécessaires. Si le patient est une femme et a moins de 30 ans, il faudra
     * au moins
     * sept termes déclencheurs. Si le patient a plus de 30 ans, alors il en faudra
     * huit ou plus.
     */
    private boolean isPatientRiskEarlyOnset(PatientDTO patient, long count) {
        if (patient.getGender().equals("M") && !isOver30yo(patient)) {
            return count >= 5;
        } else if (patient.getGender().equals("F") && !isOver30yo(patient)) {
            return count >= 7;
        } else if (isOver30yo(patient)) {
            return count >= 8;
        }
        return false;
    }

    private boolean isOver30yo(PatientDTO patient) {
        LocalDate today = LocalDate.now();
        LocalDate birthdate = patient.getBirthdate();
        Period age = Period.between(birthdate, today);
        return age.getYears() > 30;
    }

    private long countDistinctOccurrences(List<String> notes) {
        return notes.stream()
                .mapToLong(note -> terminologie.stream()  // Pour chaque note, on vérifie chaque terme
                        .filter(terme -> note.toLowerCase().contains(terme.toLowerCase()))  // Si le terme est dans la note
                        .distinct()  // On ne compte chaque terme qu'une seule fois par note
                        .count()  // On compte les termes distincts
                )
                .sum();  // Additionne les occurrences distinctes pour chaque note
    }
}
