package com.example.MedicalRecords.services;

import com.example.MedicalRecords.data.entities.Specialty;
import com.example.MedicalRecords.data.entities.User;
import com.example.MedicalRecords.dto.DoctorDTO;
import com.example.MedicalRecords.dto.SpecialtyDTO;
import com.example.MedicalRecords.exceptions.InvalidDataException;

import java.util.List;

public interface DoctorsService {
    void addOrUpdateDoctor(DoctorDTO currentDoctorDTO) throws InvalidDataException;

    void deleteDoctor(String doctorSpecialId);

    User getUser()throws InvalidDataException;

    DoctorDTO getDoctor() throws InvalidDataException;

    List<DoctorDTO> getAllDoctors();

    List<DoctorDTO> getSearchResults(DoctorDTO doctorDTO);

    List<SpecialtyDTO> getAllSpecialties();
}
