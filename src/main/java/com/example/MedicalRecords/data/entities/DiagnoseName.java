package com.example.MedicalRecords.data.entities;

public enum DiagnoseName {
    first("Diverticulosis"),
    second("Hypertonia"),
    third("Pielonefit"),
    fourth("Osteoporosis"),
    fifth("Multiple sclerosis");

    private final String displayName;

    DiagnoseName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
