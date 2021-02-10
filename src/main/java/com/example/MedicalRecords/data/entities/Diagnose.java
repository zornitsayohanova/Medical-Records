package com.example.MedicalRecords.data.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Diagnose extends BaseEntity {
    @Column
    private String diagnoseSpecialId;

    @Column
    private String diagnoseName;

    @Column
    private int diagnosePatientsAmount;
}