package com.example.MedicalRecords;

import com.example.MedicalRecords.data.dao.RoleRepository;
import com.example.MedicalRecords.data.dao.SpecialtyRepository;
import com.example.MedicalRecords.data.entities.Role;
import com.example.MedicalRecords.data.entities.Specialty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
@SpringBootApplication
public class MedicalRecordsApplication {
	@Autowired
	private static SpecialtyRepository specialtyRepository;

	public static void main(String[] args) {
		SpringApplication.run(MedicalRecordsApplication.class, args);
	}
}
