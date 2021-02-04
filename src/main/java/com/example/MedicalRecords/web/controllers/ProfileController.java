package com.example.MedicalRecords.web.controllers;

import com.example.MedicalRecords.data.dao.SpecialtyRepository;
import com.example.MedicalRecords.dto.DoctorDTO;
import com.example.MedicalRecords.dto.PatientDTO;
import com.example.MedicalRecords.data.entities.*;
import com.example.MedicalRecords.dto.SpecialtyDTO;
import com.example.MedicalRecords.exceptions.InvalidDataException;
import com.example.MedicalRecords.services.DoctorsService;
import com.example.MedicalRecords.services.PatientsService;
import com.example.MedicalRecords.web.models.DoctorViewModel;
import com.example.MedicalRecords.web.models.PatientViewModel;
import com.example.MedicalRecords.web.models.SpecialtyViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ProfileController {

    private final PatientsService patientsService;

    private final DoctorsService doctorsService;

    private final SpecialtyRepository specialtyRepository;

    private final ModelMapper modelMapper;

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    private void addProfilePageAttributes(Model model) {
        model.addAttribute("user", this.getUser());
        model.addAttribute("doctor", new DoctorViewModel());
        model.addAttribute("patient", new PatientViewModel());
    }

    @GetMapping("/profile")
    private String getPatientAndDoctor(Model model) {

        model.addAttribute("allSpecialties", doctorsService.getAllSpecialties()
                .stream()
                .map(this::convertToSpecialtyViewModel)
                .collect(Collectors.toList()));

        this.addProfilePageAttributes(model);
        return "profile";
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/profile/patient")
    private String addOrUpdatePatient(Model model, @ModelAttribute PatientViewModel patient,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "home";
        }

        try {
            patientsService.addOrUpdatePatient(modelMapper.map(patient, PatientDTO.class));
            model.addAttribute("message", "Промените са добавени");
        } catch (InvalidDataException e) {
            model.addAttribute("message", e.getMessage());
        }
        this.addProfilePageAttributes(model);

        return "profile";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/profile/doctor")
    private String viewDoctorForm(Model model) {

        this.addProfilePageAttributes(model);

        return "profile";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/profile/doctor")
    private String addOrUpdateDoctor(Model model, @ModelAttribute DoctorViewModel doctor,
                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "home";
        }

        try {
            doctorsService.addOrUpdateDoctor(modelMapper.map(doctor, DoctorDTO.class));
            model.addAttribute("message", "Промените са добавени");
        } catch (InvalidDataException e) {
            model.addAttribute("message", e.getMessage());
        }
        this.addProfilePageAttributes(model);

        return "profile";
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/medical-record")
    private String viewMedicalRecord(Model model) {
        try {
            model.addAttribute("patient", patientsService.getPatient());
        } catch (InvalidDataException e) {
            model.addAttribute("message", e.getMessage());
            this.addProfilePageAttributes(model);
            return "profile";
        }
        return "patient";
    }

    private SpecialtyViewModel convertToSpecialtyViewModel(SpecialtyDTO specialtyDTO) {
        return modelMapper.map(specialtyDTO, SpecialtyViewModel.class);
    }
}
