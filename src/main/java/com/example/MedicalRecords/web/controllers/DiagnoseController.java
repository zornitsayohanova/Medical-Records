package com.example.MedicalRecords.web.controllers;

import com.example.MedicalRecords.data.entities.Diagnose;
import com.example.MedicalRecords.dto.DiagnoseDTO;
import com.example.MedicalRecords.services.DiagnoseService;
import com.example.MedicalRecords.web.models.DiagnoseViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class DiagnoseController {
    private final DiagnoseService diagnoseService;

    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/diagnose")
    private String viewDiagnose(Model model) {
        model.addAttribute("diagnose", new Diagnose());
        model.addAttribute("AllDiagnoses", diagnoseService.getAllDiagnoses()
                .stream()
                .map(this::convertToDiagnoseViewModel)
                .collect(Collectors.toList()));

        return "diagnose-search";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/diagnose")
    private String viewDiagnose(Model model, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "home";
        }

        model.addAttribute("diagnose", new Diagnose());

        return "diagnose-search";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/diagnose/statistics")
    private String getDiagnoseStatistics(Model model, @ModelAttribute Diagnose diagnose, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "home";
        }
        Diagnose currentDiagnose = diagnoseService.getByDiagnoseId(diagnose.getId());

        model.addAttribute("message", "Вижте резултатите долу");
        model.addAttribute("diagnosePatientsAmount", currentDiagnose.getDiagnosePatientsAmount());
        model.addAttribute("diagnoseName", diagnose.getDiagnoseName());

        return "diagnose-result";
    }

    private DiagnoseViewModel convertToDiagnoseViewModel(DiagnoseDTO diagnoseDTO) {
        return modelMapper.map(diagnoseDTO, DiagnoseViewModel.class);
    }
}
