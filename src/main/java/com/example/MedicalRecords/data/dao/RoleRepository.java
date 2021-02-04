package com.example.MedicalRecords.data.dao;


import com.example.MedicalRecords.data.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(String name);
}
