package com.example.MedicalRecords.services.implementations;

import com.example.MedicalRecords.data.dao.DiagnoseRepository;
import com.example.MedicalRecords.data.dao.RoleRepository;
import com.example.MedicalRecords.data.dao.SpecialtyRepository;
import com.example.MedicalRecords.data.entities.Diagnose;
import com.example.MedicalRecords.data.entities.Role;
import com.example.MedicalRecords.data.entities.Specialty;
import com.example.MedicalRecords.services.DataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DataServiceImpl implements DataService {
    private final RoleRepository roleRepository;

    private final SpecialtyRepository specialtyRepository;

    private final DiagnoseRepository diagnoseRepository;

    @Override
    public void addEntityData() {
        if(roleRepository.findAll().size() == 0) {
            this.addRolesToEntity();
        }

        if(specialtyRepository.findAll().size() == 0) {
            this.addSpecialtiesToEntity();
        }

        if(diagnoseRepository.findAll().size() == 0) {
            this.addDiagnosesToEntity();
        }
    }

    @Override
    public void addRolesToEntity() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Role>> typeReference = new TypeReference<List<Role>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/roles.json");
        try {
            roleRepository.saveAll(mapper.readValue(inputStream,typeReference));
            System.out.println("Data records are saved!");
        } catch (IOException e) {
            System.out.println("Unable to save data: " + e.getMessage());
        }
    }

    @Override
    public void addSpecialtiesToEntity() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Specialty>> typeReference = new TypeReference<List<Specialty>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/specialties.json");
        try {
            specialtyRepository.saveAll(mapper.readValue(inputStream,typeReference));
            System.out.println("Data records are saved!");
        } catch (IOException e){
            System.out.println("Unable to save data: " + e.getMessage());
        }
    }

    @Override
    public void addDiagnosesToEntity() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Diagnose>> typeReference = new TypeReference<List<Diagnose>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/diagnoses.json");
        try {
            diagnoseRepository.saveAll(mapper.readValue(inputStream,typeReference));
            System.out.println("Data records are saved!");
        } catch (IOException e){
            System.out.println("Unable to save data: " + e.getMessage());
        }
    }
}
