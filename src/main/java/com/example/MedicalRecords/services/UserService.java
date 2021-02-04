package com.example.MedicalRecords.services;

import com.example.MedicalRecords.data.entities.User;
import com.example.MedicalRecords.exceptions.InvalidDataException;
import com.example.MedicalRecords.exceptions.UserAlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    void registerUser(User user) throws UserAlreadyExistsException, InvalidDataException;

    void deleteUser(String specialId);

    void updateUser(User currentUser) throws UserAlreadyExistsException, InvalidDataException;

    boolean checkIfUserExist(String username);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
