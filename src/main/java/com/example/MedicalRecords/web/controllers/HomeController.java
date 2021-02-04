package com.example.MedicalRecords.web.controllers;
import com.example.MedicalRecords.services.DataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class HomeController {

    private final DataService dataService;

    @GetMapping("/")
    public String getHome() {
        dataService.addEntityData();

        return "home";
    }

    @GetMapping("/unauthorized")
    public String getUnauthorized() {
        return "unauthorized";
    }
}

