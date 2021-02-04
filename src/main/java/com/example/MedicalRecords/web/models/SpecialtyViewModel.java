package com.example.MedicalRecords.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyViewModel {
    private Long id;

    private String specialtySpecialId;

    private String specialtyName;
}
