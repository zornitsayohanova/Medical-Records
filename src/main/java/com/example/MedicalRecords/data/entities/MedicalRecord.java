package com.example.MedicalRecords.data.entities;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class MedicalRecord extends BaseEntity {
    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @Column
    @Temporal(DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date visitDate;

    @ManyToOne
    private Diagnose diagnose;

    @Column
    private String medication;

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date treatmentStartDate;

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date treatmentEndDate;
}
