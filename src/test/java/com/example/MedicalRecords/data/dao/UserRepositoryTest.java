package com.example.MedicalRecords.data.dao;

import com.example.MedicalRecords.data.entities.User;
import com.example.MedicalRecords.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @MockBean
    private TestEntityManager testEntityManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    UserServiceImpl userServiceImpl;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void findByUsername() {
        String username = "P4356";

        User user = new User();
        user.setUsername(username);
        user.setPassword("1234");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        testEntityManager.persistAndFlush(user);

        User foundUser = userRepository.findByUsername(username);

        assertThat(foundUser.getUsername()).isEqualTo(username);
    }
}


