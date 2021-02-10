package com.example.MedicalRecords.data.dao;

import com.example.MedicalRecords.data.entities.Diagnose;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class DiagnoseRepositoryTest {

    @MockBean
    private TestEntityManager testEntityManager;

    @MockBean
    private DiagnoseRepository diagnoseRepository;

    @Test
    @WithMockUser(username="D1234",authorities={"DOCTOR"})
    void findByDiagnoseName() {
        String diagnoseName = "Анемия";
        Diagnose diagnose = new Diagnose();
        diagnose.setDiagnoseName(diagnoseName);
        diagnose.setDiagnosePatientsAmount(10);
        diagnose.setDiagnoseSpecialId("34");
        testEntityManager.persistAndFlush(diagnose);

        Diagnose foundDiagnose = diagnoseRepository.findByDiagnoseName(diagnoseName);

        assertThat(foundDiagnose.getDiagnoseName()).isEqualTo(diagnose.getDiagnoseName());
    }
}

