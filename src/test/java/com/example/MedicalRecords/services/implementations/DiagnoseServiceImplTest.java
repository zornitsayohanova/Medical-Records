package com.example.MedicalRecords.services.implementations;

import com.example.MedicalRecords.data.dao.DiagnoseRepository;
import com.example.MedicalRecords.data.entities.Diagnose;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class DiagnoseServiceImplTest {

    @MockBean
    private TestEntityManager testEntityManager;

    @MockBean
    private DiagnoseRepository diagnoseRepository;

    @MockBean
    UserServiceImpl userServiceImpl;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @WithMockUser(username="D1234",authorities={"DOCTOR"})
    void getDiagnosePatientsAmount() {
        String diagnoseName = "Анемия";
        Diagnose diagnose = new Diagnose();
        diagnose.setDiagnoseName(diagnoseName);
        diagnose.setDiagnosePatientsAmount(10);
        diagnose.setDiagnoseSpecialId("10");
        testEntityManager.persistAndFlush(diagnose);

        when(diagnoseRepository.findByDiagnoseName(diagnoseName).getDiagnosePatientsAmount())
                .thenReturn(10);

        int amount = diagnoseRepository.findByDiagnoseName(diagnoseName).getDiagnosePatientsAmount();
        assertEquals(10, amount);
    }
}