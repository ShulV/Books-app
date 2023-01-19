package org.app1.SpringBootJpaSecurity.controllers;

import org.app1.SpringBootJpaSecurity.models.Person;
import org.app1.SpringBootJpaSecurity.services.PersonImageService;
import org.app1.SpringBootJpaSecurity.services.RegistrationService;
import org.app1.SpringBootJpaSecurity.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;

    private final PersonImageService personImageService;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, PersonImageService personImageService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.personImageService = personImageService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@RequestParam("file") MultipartFile file, @ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) throws IOException {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }

        registrationService.register(person);
        personImageService.addImage(file, person);
        return "redirect:/auth/login";
    }
}
