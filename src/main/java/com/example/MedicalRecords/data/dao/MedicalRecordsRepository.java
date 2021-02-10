package com.example.MedicalRecords.data.dao;

import com.example.MedicalRecords.data.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecord, Long> {
}