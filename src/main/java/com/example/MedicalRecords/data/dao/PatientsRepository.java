package com.example.MedicalRecords.data.dao;

import com.example.MedicalRecords.data.entities.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface PatientsRepository extends CrudRepository<Patient, Long> {
    Patient findByPatientSpecialId(String id);

  //  List<Patient> findAll();
    List<Patient> findAllByFirstNameOrThirdNameOrEgn(String firstName, String secondName, String egn);
    List<Patient> findAllByFirstNameAndThirdName(String firstName, String secondName);
}
