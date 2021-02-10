package com.example.MedicalRecords.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiagnoseViewModel {
    private long id;

    private String diagnoseSpecialId;

    private String diagnoseName;

    private int diagnosePatientsAmount;
}
