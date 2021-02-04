package com.example.MedicalRecords.data.entities;

public enum SpecialtyName {
    first("Гастроентерология"),
    second("Кардиология"),
    third("Нефрология"),
    fourth("Ортопедия и травматология"),
    fifth("Нервни болести");

    private final String displayName;

    SpecialtyName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}