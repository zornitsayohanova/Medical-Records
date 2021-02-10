package com.example.MedicalRecords.services;

import com.example.MedicalRecords.data.entities.Diagnose;
import com.example.MedicalRecords.dto.DiagnoseDTO;

import java.util.List;

public interface DiagnoseService {
    Diagnose getByDiagnoseId(Long id);

    List<DiagnoseDTO> getAllDiagnoses();
}
