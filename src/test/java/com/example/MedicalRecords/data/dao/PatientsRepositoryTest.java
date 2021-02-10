package com.example.MedicalRecords.data.dao;

import com.example.MedicalRecords.data.entities.Patient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class PatientsRepositoryTest {
    @MockBean
    private TestEntityManager testEntityManager;

    @MockBean
    private PatientsRepository patientsRepository;

    @Test
    void findByPatientSpecialId() {
        String specialId = "P3456";
        Patient patient = new Patient();
        patient.setPatientSpecialId(specialId);
        patient.setEgn("9809081230");
        patient.setFirstName("Мария");
        patient.setSecondName("Иванова");
        patient.setThirdName("Георгиева");
        patient.setInsurancePaid(false);
        testEntityManager.persistAndFlush(patient);

        Patient foundPatient = patientsRepository.findByPatientSpecialId(specialId);

        assertThat(foundPatient.getPatientSpecialId()).isEqualTo(specialId);
    }

    @Test
    void findAllByFirstNameOrThirdNameOrEgn() {
        String egn = "9809081230";
        String firstName = "Мария";
        String thirdName = "Георгиева";

        Patient patient = new Patient();
        patient.setPatientSpecialId("P1234");
        patient.setEgn(egn);
        patient.setFirstName(firstName);
        patient.setSecondName("Иванова");
        patient.setThirdName(thirdName);
        patient.setInsurancePaid(false);
        testEntityManager.persistAndFlush(patient);

        Patient patient2 = new Patient();
        patient2.setPatientSpecialId("P1634");
        patient2.setEgn("9802081236");
        patient2.setFirstName(firstName);
        patient2.setSecondName("Енчева");
        patient2.setThirdName(thirdName);
        patient2.setInsurancePaid(false);
        testEntityManager.persistAndFlush(patient2);

        assertThat(patientsRepository.
                findAllByFirstNameOrThirdNameOrEgn(egn, egn, egn).size()).isEqualTo(1);

        assertThat(patientsRepository.
                findAllByFirstNameOrThirdNameOrEgn(firstName, firstName, firstName).size()).isEqualTo(2);

        assertThat(patientsRepository.
                findAllByFirstNameOrThirdNameOrEgn(thirdName, thirdName, thirdName).size()).isEqualTo(2);
    }

    @Test
    void findAllByFirstNameAndThirdName() {
        String firstName = "Мария";
        String thirdName = "Георгиева";

        Patient patient = new Patient();
        patient.setPatientSpecialId("P1234");
        patient.setEgn("9809081230");
        patient.setFirstName(firstName);
        patient.setSecondName("Иванова");
        patient.setThirdName(thirdName);
        patient.setInsurancePaid(false);
        testEntityManager.persistAndFlush(patient);

        Patient patient2 = new Patient();
        patient2.setPatientSpecialId("P1634");
        patient2.setEgn("9802081236");
        patient2.setFirstName(firstName);
        patient2.setSecondName("Енчева");
        patient2.setThirdName(thirdName);
        patient2.setInsurancePaid(false);
        testEntityManager.persistAndFlush(patient2);

        assertThat(patientsRepository.
                findAllByFirstNameAndThirdName(firstName, thirdName).size()).isEqualTo(2);
    }
}


