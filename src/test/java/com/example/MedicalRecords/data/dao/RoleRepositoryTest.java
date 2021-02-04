package com.example.MedicalRecords.data.dao;

import com.example.MedicalRecords.data.entities.Role;
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
class RoleRepositoryTest {

    @MockBean
    private TestEntityManager testEntityManager;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void findByAuthority() {
        String authorityName = "PATIENT1";
        Role authority = new Role();
        authority.setAuthority(authorityName);
        testEntityManager.persistAndFlush(authority);

        Role foundAuthority = roleRepository.findByAuthority(authorityName);

        assertThat(foundAuthority.getAuthority()).isEqualTo(authorityName);
    }
}
