package com.example.MedicalRecords.services.implementations;

import com.example.MedicalRecords.data.dao.DoctorsRepository;
import com.example.MedicalRecords.data.dao.SpecialtyRepository;
import com.example.MedicalRecords.data.entities.Doctor;
import com.example.MedicalRecords.services.DoctorsService;
import com.example.MedicalRecords.services.UserService;
import com.example.MedicalRecords.web.controllers.DoctorsController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@WebMvcTest(DoctorsController.class)
class DoctorsServiceTest {

    @MockBean
    private DoctorsRepository doctorsRepository;

    @MockBean
    private TestEntityManager testEntityManager;

    @MockBean
    private SpecialtyRepository specialtyRepository;

    @MockBean
    private DoctorsService doctorsService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    UserServiceImpl userServiceImpl;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @WithMockUser(username="D1234",authorities={"DOCTOR"})
    void addOrUpdateDoctor() {
        String specialId = "D1234";
        Doctor doctor = new Doctor();
        doctor.setDoctorSpecialId(specialId);
        doctor.setFirstName("Валентин");
        doctor.setSecondName("Петров");
        doctor.setThirdName("Михайлов");
        doctor.setGP(true);
        testEntityManager.persistAndFlush(doctor);
        when(doctorsRepository.save(doctor)).thenReturn(doctor);


        assertEquals( 1, doctorsRepository.findAll().size());
    }

    @Test
    @WithMockUser(username="D1234",authorities={"DOCTOR"})
    void deleteDoctor() {
        String specialId = "D1234";
        Doctor doctor = new Doctor();
        doctor.setDoctorSpecialId(specialId);
        doctor.setDeleted(true);

        when(doctorsRepository.save(doctor)).thenReturn(doctor);

        Doctor foundDoctor = doctorsRepository.findByDoctorSpecialId(specialId);

        assertNull(foundDoctor);
    }

    @Test
    @WithMockUser(username="D1234",authorities={"DOCTOR"})
    void getDoctor() {
        String specialId = "D1234";

        Doctor doctor = new Doctor();
        doctor.setDoctorSpecialId(specialId);

        when(doctorsRepository.findByDoctorSpecialId(specialId)).thenReturn(doctor);

        Doctor foundDoctor = doctorsRepository.findByDoctorSpecialId(specialId);

        assertNotEquals(foundDoctor, null);
    }

    @Test
    void getAllDoctors() {
        String specialId = "D1234";

        Doctor doctor = new Doctor();
        doctor.setDoctorSpecialId(specialId);
        testEntityManager.persistAndFlush(doctor);

        when(doctorsRepository.findAll()).thenReturn(Collections.singletonList(doctor));
        List<Doctor> allDoctors = doctorsRepository.findAll();

        assertEquals(1, allDoctors.size());
    }
}