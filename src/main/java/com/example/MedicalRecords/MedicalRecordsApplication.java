package com.example.MedicalRecords;

import com.example.MedicalRecords.data.dao.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class MedicalRecordsApplication {
	public static void main(String[] args) {
		SpringApplication.run(MedicalRecordsApplication.class, args);
	}
}
