package com.example.MedicalRecords.web.models;

import com.example.MedicalRecords.data.entities.Diagnose;
import com.example.MedicalRecords.data.entities.Doctor;
import com.example.MedicalRecords.data.entities.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordViewModel {
    private Patient patient;

    private Doctor doctor;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date visitDate;

    private Diagnose diagnose;

    private String medication;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date treatmentStartDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date treatmentEndDate;
}
