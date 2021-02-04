package com.example.MedicalRecords.services;

import com.example.MedicalRecords.data.entities.*;
import com.example.MedicalRecords.dto.MedicalRecordDTO;
import com.example.MedicalRecords.dto.PatientDTO;
import com.example.MedicalRecords.exceptions.InvalidDataException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PatientsService {
    List<PatientDTO> getAllPatients();

    void addOrUpdatePatient (PatientDTO currentPatientDTO) throws InvalidDataException;

    void deletePatient(String patientSpecialId);

    List<PatientDTO> getSearchResults(PatientDTO patientDTO);

    @Transactional
    void addMedicalRecord(String patientSpecialId, MedicalRecordDTO medicalRecordDTO) throws InvalidDataException;

    User getUser() throws InvalidDataException;

    PatientDTO getPatient(String patientSpecialId);

    PatientDTO getPatient() throws InvalidDataException;
}
