package com.example.MedicalRecords.web.models;

import com.example.MedicalRecords.data.entities.Patient;
import com.example.MedicalRecords.data.entities.Specialty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorViewModel {
    private long id;

    private String doctorSpecialId;

    private String firstName;

    private String secondName;

    private String thirdName;

    private List<Specialty> specialties;

    private boolean isGP;

    private List<Patient> patients;
}