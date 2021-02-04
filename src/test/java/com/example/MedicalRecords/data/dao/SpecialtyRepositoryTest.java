package com.example.MedicalRecords.data.dao;

import com.example.MedicalRecords.data.entities.Specialty;
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
class SpecialtyRepositoryTest {

    @MockBean
    private TestEntityManager testEntityManager;

    @MockBean
    private SpecialtyRepository specialtyRepository;

    @Test
    void findBySpecialtyName() {
        String specialtyName = "Гатроентерология";
        Specialty specialty = new Specialty();
        specialty.setSpecialtyName(specialtyName);
        specialty.setSpecialtySpecialId("1");
        testEntityManager.persistAndFlush(specialty);

        Specialty foundSpecialty = specialtyRepository.findBySpecialtyName(specialtyName);

        assertThat(foundSpecialty.getSpecialtyName()).isEqualTo(specialty.getSpecialtyName());
    }
}



