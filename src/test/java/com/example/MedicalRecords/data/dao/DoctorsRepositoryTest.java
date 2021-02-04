package com.example.MedicalRecords.data.dao;

import com.example.MedicalRecords.data.entities.Doctor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class DoctorsRepositoryTest {

    @MockBean
    private DoctorsRepository doctorsRepository;

    @MockBean
    private TestEntityManager testEntityManager;

    @Test
    void findByDoctorSpecialId() {
        String specialId = "D123456";

        Doctor doctor = new Doctor();
        doctor.setDoctorSpecialId(specialId);
        doctor.setFirstName("Валентин");
        doctor.setSecondName("Петров");
        doctor.setThirdName("Михайлов");
        doctor.setGP(true);
        testEntityManager.persistAndFlush(doctor);

        Doctor foundDoctor = doctorsRepository.findByDoctorSpecialId(specialId);

        assertThat(foundDoctor.getDoctorSpecialId()).isEqualTo(specialId);
    }

    @Test
    void findAllByFirstNameOrThirdName() {
        String firstName = "Валентин";
        String thirdName = "Михайлов";

        Doctor doctor = new Doctor();
        doctor.setDoctorSpecialId("D123456");
        doctor.setFirstName(firstName);
        doctor.setSecondName("Петров");
        doctor.setThirdName(thirdName);
        doctor.setGP(true);
        testEntityManager.persistAndFlush(doctor);

        Doctor doctor2 = new Doctor();
        doctor2.setDoctorSpecialId("D126787");
        doctor2.setFirstName(firstName);
        doctor2.setSecondName("Иванов");
        doctor2.setThirdName(thirdName);
        doctor2.setGP(true);
        testEntityManager.persistAndFlush(doctor2);

        assertThat(doctorsRepository.
                findAllByFirstNameOrThirdName(firstName, firstName).size()).isEqualTo(2);

        assertThat(doctorsRepository.
                findAllByFirstNameOrThirdName(thirdName, thirdName).size()).isEqualTo(2);
    }

    @Test
    void findAllByFirstNameAndThirdName() {
        String firstName = "Валентин";
        String thirdName = "Михайлов";

        Doctor doctor = new Doctor();
        doctor.setDoctorSpecialId("D123456");
        doctor.setFirstName(firstName);
        doctor.setSecondName("Петров");
        doctor.setThirdName(thirdName);
        doctor.setGP(true);
        testEntityManager.persistAndFlush(doctor);

        Doctor doctor2 = new Doctor();
        doctor2.setDoctorSpecialId("D126787");
        doctor2.setFirstName(firstName);
        doctor2.setSecondName("Иванов");
        doctor2.setThirdName(thirdName);
        doctor2.setGP(true);
        testEntityManager.persistAndFlush(doctor2);

        assertThat(doctorsRepository.
                findAllByFirstNameOrThirdName(firstName, thirdName).size()).isEqualTo(2);
    }
}
