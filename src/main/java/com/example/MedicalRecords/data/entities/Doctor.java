package com.example.MedicalRecords.data.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "is_deleted='false'")
public class Doctor extends BaseEntity {
    @Column
    private String doctorSpecialId;

    @Column
    private String firstName;

    @Column
    private String secondName;

    @Column
    private String thirdName;

    @OneToMany
    @Column
    private List<Specialty> specialties;

    @Column
    private boolean isGP;

    @OneToMany
    @Column
    private List<Patient> patients;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
