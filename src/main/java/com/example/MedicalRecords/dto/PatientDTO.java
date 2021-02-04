package com.example.MedicalRecords.dto;

import com.example.MedicalRecords.data.entities.Doctor;
import com.example.MedicalRecords.data.entities.MedicalRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDTO {
    private long id;

    private String patientSpecialId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String secondName;

    @NotBlank
    private String thirdName;

    @NotNull
    @Size(min = 10, max = 10, message="Min 10, Max 10")
    private String egn;

    private Doctor gp;

    private boolean isInsurancePaid;

    private List<MedicalRecord> medicalRecords;
}
