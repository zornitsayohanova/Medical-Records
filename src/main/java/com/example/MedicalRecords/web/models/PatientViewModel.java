package com.example.MedicalRecords.web.models;

import com.example.MedicalRecords.data.entities.Doctor;
import com.example.MedicalRecords.data.entities.MedicalRecord;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PatientViewModel {
    private String patientSpecialId;

    private String egn;

    private String firstName;

    private String secondName;

    private String thirdName;

    private Doctor gp;

    private boolean isInsurancePaid;

    private List<MedicalRecord> medicalRecords;
}
