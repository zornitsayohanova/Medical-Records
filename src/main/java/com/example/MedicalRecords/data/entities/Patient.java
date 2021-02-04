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
public class Patient extends BaseEntity {
    @Column
    private String patientSpecialId;

    @Column
    private String egn;

    @Column
    private String firstName;

    @Column
    private String secondName;

    @Column
    private String thirdName;

    @ManyToOne
    private Doctor gp;

    @Column
    private boolean isInsurancePaid;

    @OneToMany
    @Column
    private List<MedicalRecord> medicalRecords;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
