package com.example.MedicalRecords.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiagnoseDTO {
    private long id;

    private String diagnoseSpecialId;

    private String diagnoseName;

    private int diagnosePatientsAmount;
}
