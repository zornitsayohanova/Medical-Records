package com.example.MedicalRecords.services.implementations;
import com.example.MedicalRecords.data.dao.*;
import com.example.MedicalRecords.data.entities.Diagnose;
import com.example.MedicalRecords.data.entities.Doctor;
import com.example.MedicalRecords.dto.DiagnoseDTO;
import com.example.MedicalRecords.dto.DoctorDTO;
import com.example.MedicalRecords.services.DiagnoseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DiagnoseServiceImpl implements DiagnoseService {

    private final DiagnoseRepository diagnoseRepository;

    private final ModelMapper modelMapper;

    @Override
    public int getDiagnosePatientsAmount(String diagnoseName) {
        return diagnoseRepository.findByDiagnoseName(diagnoseName).getDiagnosePatientsAmount();
    }

    public List<DiagnoseDTO> getAllDiagnoses() {
        return diagnoseRepository.findAll()
                .stream()
                .map(this::convertToDiagnoseDTO)
                .collect(Collectors.toList());
    }

    private DiagnoseDTO convertToDiagnoseDTO(Diagnose diagnose) {
        return modelMapper.map(diagnose, DiagnoseDTO.class);
    }
}
