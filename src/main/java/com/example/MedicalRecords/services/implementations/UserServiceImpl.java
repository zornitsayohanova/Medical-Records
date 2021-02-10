package com.example.MedicalRecords.services.implementations;

import com.example.MedicalRecords.data.entities.*;
import com.example.MedicalRecords.data.dao.*;
import com.example.MedicalRecords.exceptions.InvalidDataException;
import com.example.MedicalRecords.exceptions.UserAlreadyExistsException;
import com.example.MedicalRecords.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void registerUser(User user) throws UserAlreadyExistsException, InvalidDataException {
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        if(checkIfUserExist(user.getUsername())){
            throw new UserAlreadyExistsException("Този потребител вече съществува!");
        } else {
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setAccountNonExpired(true);
            user.setEnabled(true);

            Set<Role> authorities = new HashSet<Role>(){};

            if(user.getUsername().contains("D")) {
                Role authority = roleRepository.findByAuthority("DOCTOR");
                authorities.add(authority);
                user.setAuthorities(authorities);
            } else if(user.getUsername().contains("P")) {
                Role authority = roleRepository.findByAuthority("PATIENT");
                authorities.add(authority);
                user.setAuthorities(authorities);
            } else if(user.getUsername().contains("A")) {
                Role authority = roleRepository.findByAuthority("ADMIN");
                authorities.add(authority);
            } else {
               throw new InvalidDataException("Потребителското име е невалидно!");
            }
            userRepository.save(user);
        }
    }

    @Override
    public void updateUser(User currentUser) throws UserAlreadyExistsException, InvalidDataException {
        User user = userRepository.findByUsername(currentUser.getUsername());

        if(user != null) {
            if(!bCryptPasswordEncoder.matches(currentUser.getPassword(),user.getPassword())) {
                String newPassword = currentUser.getPassword();
                String newEncryptedPassword = bCryptPasswordEncoder.encode(newPassword);
                user.setPassword(newEncryptedPassword);

                userRepository.save(user);
            } else {
                throw new InvalidDataException("Новата парола трябва да е различна от старата!");
            }
        } else {
            throw new UserAlreadyExistsException("Вашето ID не съществува. Моля, обърнете се към поддръжка.");
        }
    }

    @Override
    public void deleteUser(String specialId) {
        User user = (User) this.loadUserByUsername(specialId);

        user.setAccountNonLocked(false);
        user.setCredentialsNonExpired(false);
        user.setAccountNonExpired(false);
        user.setEnabled(false);
    }

    @Override
    public boolean checkIfUserExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}