package com.example.MedicalRecords.data.dao;


import com.example.MedicalRecords.data.entities.Diagnose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {
    Diagnose findByDiagnoseName(String name);
}
