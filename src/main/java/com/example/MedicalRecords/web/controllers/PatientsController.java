package com.example.MedicalRecords.web.controllers;
import com.example.MedicalRecords.data.entities.Diagnose;
import com.example.MedicalRecords.dto.DiagnoseDTO;
import com.example.MedicalRecords.dto.MedicalRecordDTO;
import com.example.MedicalRecords.dto.PatientDTO;
import com.example.MedicalRecords.exceptions.InvalidDataException;
import com.example.MedicalRecords.services.DiagnoseService;
import com.example.MedicalRecords.services.PatientsService;
import com.example.MedicalRecords.services.UserService;
import com.example.MedicalRecords.web.models.DiagnoseViewModel;
import com.example.MedicalRecords.web.models.MedicalRecordViewModel;
import com.example.MedicalRecords.web.models.PatientViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class PatientsController {
    private final PatientsService patientsService;

    private final DiagnoseService diagnoseService;

    private final ModelMapper modelMapper;

    private final UserService userService;

    private void addPatientPageAttributes(Model model, String patientSpecialId,
                                          MedicalRecordViewModel medicalRecordViewModel) {
        model.addAttribute("patient", modelMapper.map(patientsService.getPatient(patientSpecialId),
                PatientViewModel.class));
        model.addAttribute("medicalRecord", medicalRecordViewModel);
    }


    private void addPatientsPageAttributes(Model model) {
        model.addAttribute("patientToSearch", new PatientViewModel());
        model.addAttribute("resultPatients", patientsService.getAllPatients()
                .stream()
                .map(this::convertToPatientViewModel)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('DOCTOR, ADMIN')")
    @GetMapping("/patients")
    private String getAllPatients(Model model) {
        this.addPatientsPageAttributes(model);

        return "patients";
    }

    @PostMapping("/patients/delete-patient")
    private String deletePatient(Model model, @RequestParam("patientSpecialId") String patientSpecialId,
                                @ModelAttribute PatientViewModel patientViewModel,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "home";
        }

        patientsService.deletePatient(patientSpecialId);
        userService.deleteUser(patientSpecialId);
        model.addAttribute("message", "Успешно изтрит пациент.");

        this.addPatientsPageAttributes(model);

        return "patients";
    }

    @PreAuthorize("hasAnyRole('DOCTOR, ADMIN')")
    @PostMapping("/patients")
    private String getSearchedPatient(Model model, @ModelAttribute("patientToSearch") PatientViewModel patientViewModel,
                                      BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "home";
        }

        model.addAttribute("message", "Вижте резултатите долу");
        model.addAttribute("patientToSearch", patientViewModel);
        model.addAttribute("resultPatients", patientsService.
                getSearchResults(modelMapper.map(patientViewModel, PatientDTO.class))
                .stream()
                .map(this::convertToPatientViewModel)
                .collect(Collectors.toList()));

        return "patients";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("patients/patient")
    private String getPatient(Model model, @RequestParam("patientSpecialId") String patientSpecialId) {
        addPatientPageAttributes(model, patientSpecialId, new MedicalRecordViewModel());

        return "patient";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("patients/patient")
    private String setPatient(Model model, @RequestParam("patientSpecialId") String patientSpecialId,
                              @ModelAttribute MedicalRecordViewModel medicalRecordViewModel,
                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "home";
        }

        model.addAttribute("AllDiagnoses", diagnoseService.getAllDiagnoses()
                .stream()
                .map(this::convertToDiagnoseViewModel)
                .collect(Collectors.toList()));
        addPatientPageAttributes(model, patientSpecialId, medicalRecordViewModel);

        return "patient";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("patients/patient/add-record")
    private String addMedicalRecord(Model model, @RequestParam("patientSpecialId") String patientSpecialId,
                          @ModelAttribute MedicalRecordViewModel medicalRecordViewModel,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "home";
        }

        Diagnose currentDiagnose = diagnoseService.getByDiagnoseId(medicalRecordViewModel.getDiagnose().getId());
        medicalRecordViewModel.setDiagnose(currentDiagnose);

        try {
            patientsService.addMedicalRecord(patientSpecialId,
                    modelMapper.map(medicalRecordViewModel, MedicalRecordDTO.class));
            model.addAttribute("message", "Диагнозата е добавена успешно.");
        } catch (InvalidDataException e) {
            model.addAttribute("message", e.getMessage());
        }

        addPatientPageAttributes(model, patientSpecialId, medicalRecordViewModel);
        return "patient";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private PatientViewModel convertToPatientViewModel(PatientDTO patientDTO) {
        return modelMapper.map(patientDTO, PatientViewModel.class);
    }

    private DiagnoseViewModel convertToDiagnoseViewModel(DiagnoseDTO diagnoseDTO) {
        return modelMapper.map(diagnoseDTO, DiagnoseViewModel.class);
    }
}