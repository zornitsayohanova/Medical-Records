package com.example.MedicalRecords.data.dao;

import com.example.MedicalRecords.data.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    Specialty findBySpecialtyName(String id);
}