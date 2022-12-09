package org.app1.controllers;

import org.app1.services.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorsController {
    private final AuthorsService authorsService;

    @Autowired
    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    //запрос на получение страницы со списком всех авторов
    @GetMapping()
    public String getAuthorsPage(Model model) {
        model.addAttribute("authors", authorsService.getAllAuthors());
        return "authors/all-authors";
    }
}
