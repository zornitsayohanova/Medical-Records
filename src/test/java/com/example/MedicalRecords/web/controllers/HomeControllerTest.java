package com.example.MedicalRecords.web.controllers;

import com.example.MedicalRecords.services.implementations.DataServiceImpl;
import com.example.MedicalRecords.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@WebMvcTest(HomeController.class)
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DataServiceImpl dataServiceImpl;

    @MockBean
    UserServiceImpl userServiceImpl;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void getHome() throws Exception {
        dataServiceImpl.addEntityData();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @WithMockUser(value = "spring")
    @Test
    void getUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/unauthorized"))
                .andExpect(status().isOk())
                .andExpect(view().name("unauthorized"));
    }
}
