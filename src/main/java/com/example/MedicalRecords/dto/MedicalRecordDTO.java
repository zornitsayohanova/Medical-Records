package com.example.MedicalRecords.dto;

import com.example.MedicalRecords.data.entities.Diagnose;
import com.example.MedicalRecords.data.entities.Doctor;
import com.example.MedicalRecords.data.entities.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MedicalRecordDTO {
    private Patient patient;

    private Doctor doctor;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date visitDate;

    private Diagnose diagnose;

    private String medication;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message="Датата трябва да е в бъдещето!")
    private Date treatmentStartDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message="Датата трябва да е в бъдещето!")
    private Date treatmentEndDate;
}
