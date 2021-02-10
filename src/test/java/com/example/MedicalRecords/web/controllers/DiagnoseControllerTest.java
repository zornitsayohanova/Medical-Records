package com.example.MedicalRecords.web.controllers;

import com.example.MedicalRecords.services.implementations.DataServiceImpl;
import com.example.MedicalRecords.services.implementations.DiagnoseServiceImpl;
import com.example.MedicalRecords.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DiagnoseController.class)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class DiagnoseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DataServiceImpl dataServiceImpl;

    @MockBean
    DiagnoseServiceImpl diagnoseServiceImpl;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    UserServiceImpl userServiceImpl;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @WithMockUser(username="D1234",authorities={"DOCTOR"})
    public void viewDiagnose() throws Exception {

       mockMvc.perform(MockMvcRequestBuilders.get("/diagnose"))
               .andExpect(status().isOk())
               .andExpect(view().name("diagnose-search"));
    }
}
