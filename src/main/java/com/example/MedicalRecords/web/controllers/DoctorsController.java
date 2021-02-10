package com.example.MedicalRecords.web.controllers;
import com.example.MedicalRecords.dto.DoctorDTO;
import com.example.MedicalRecords.services.DoctorsService;
import com.example.MedicalRecords.services.UserService;
import com.example.MedicalRecords.web.models.DoctorViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class DoctorsController {
    private final DoctorsService doctorsService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private void addDoctorsPageAttributes(Model model) {
        model.addAttribute("doctorToSearch", new DoctorViewModel());
        model.addAttribute("resultDoctors", doctorsService.getAllDoctors()
                .stream()
                .map(this::convertToDoctorViewModel)
                .collect(Collectors.toList()));
    }

    @GetMapping("/doctors")
    private String getAllDoctors(Model model) {
        this.addDoctorsPageAttributes(model);

        return "doctors";
    }

    @PostMapping("/doctors")
    private String getSearchedDoctor(Model model, @ModelAttribute("doctorToSearch") DoctorViewModel doctorViewModel,
                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
           return "home";
        }

        model.addAttribute("message", "Вижте резултатите долу");
        model.addAttribute("doctorToSearch", doctorViewModel);
        model.addAttribute("resultDoctors", doctorsService
                .getSearchResults(modelMapper.map(doctorViewModel, DoctorDTO.class))
                .stream()
                .map(this::convertToDoctorViewModel)
                .collect(Collectors.toList()));

        return "doctors";
    }

    @PostMapping("/doctors/delete-doctor")
    private String deleteDoctor(Model model, @RequestParam("doctorSpecialId") String doctorSpecialId,
                                @ModelAttribute DoctorViewModel doctorViewModel,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "home";
        }
        doctorsService.deleteDoctor(doctorSpecialId);
        userService.deleteUser(doctorSpecialId);
        model.addAttribute("message", "Успешно изтрит доктор.");

        addDoctorsPageAttributes(model);

        return "doctors";
    }

    private DoctorViewModel convertToDoctorViewModel(DoctorDTO doctorDTO) {
        return modelMapper.map(doctorDTO, DoctorViewModel.class);
    }
}