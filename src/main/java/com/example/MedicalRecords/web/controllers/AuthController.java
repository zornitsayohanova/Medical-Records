package com.example.MedicalRecords.web.controllers;

import com.example.MedicalRecords.data.entities.Doctor;
import com.example.MedicalRecords.data.entities.Patient;
import com.example.MedicalRecords.data.entities.User;
import com.example.MedicalRecords.exceptions.InvalidDataException;
import com.example.MedicalRecords.exceptions.UserAlreadyExistsException;
import com.example.MedicalRecords.services.implementations.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/register")
    public String viewUserRegisterForm(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Model model,
                               @ModelAttribute User user, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "home";
        }

        model.addAttribute("user", new User());
        try {
            userService.registerUser(user);
        } catch (UserAlreadyExistsException | InvalidDataException e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("message", e.getMessage());

            return "register";
        }
        return "redirect:/login";
    }

    @PostMapping("/new-password")
    public String updateUser(Model model, @RequestParam(required = false) String error, User user) {
        if(error != null) {
            model.addAttribute("error", "Грешни данни!");
        }

        if(this.getUser() != null) {
            model.addAttribute("user", this.getUser());
            try {
                user.setUsername(this.getUser().getUsername());
                userService.updateUser(user);
                model.addAttribute("message", "Успешна смяна на паролата");
            } catch (InvalidDataException | UserAlreadyExistsException e) {
                model.addAttribute("message", e.getMessage());
            }
        }
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("patient", new Patient());

        return "profile";
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/login")
    public String loginUser(@RequestParam(required = false) String error, Model model) {
        if(error != null) {
            model.addAttribute("error", "Грешни данни!");
           // return "login";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
