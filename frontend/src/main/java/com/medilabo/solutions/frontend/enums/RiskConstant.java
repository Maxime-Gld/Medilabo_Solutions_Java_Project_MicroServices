package com.medilabo.solutions.frontend.enums;

public enum RiskConstant {
    NO_CONCLUSION("Risque non concluant"),
    NONE("Aucun risque"),
    BORDERLINE("Risque : limite"),
    IN_DANGER("Risque : dangereux"),
    EARLY_ONSET("Risque : apparition précoce");

    private final String message;

    RiskConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static RiskConstant fromString(String value) {
        try {
            return RiskConstant.valueOf(value);
        } catch (IllegalArgumentException e) {
            return NO_CONCLUSION;
        }
    }
}

