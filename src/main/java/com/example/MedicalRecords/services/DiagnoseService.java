package com.example.MedicalRecords.services;

import com.example.MedicalRecords.dto.DiagnoseDTO;

import java.util.List;

public interface DiagnoseService {
    int getDiagnosePatientsAmount(String diagnoseName);

    public List<DiagnoseDTO> getAllDiagnoses();
}
