package com.example.MedicalRecords.data.dao;

import com.example.MedicalRecords.data.entities.Doctor;
import com.example.MedicalRecords.data.entities.Patient;
import com.example.MedicalRecords.dto.DoctorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface DoctorsRepository extends JpaRepository<Doctor, Long> {
    Doctor findByDoctorSpecialId(String id);
    List<Doctor> findAllByFirstNameOrThirdName(String firstName, String thirdName);
    List<Doctor> findAllByFirstNameAndThirdName(String firstName, String thirdName);
   // List<Doctor> findAll();
}