package org.app1.controllers;

import org.app1.models.Author;
import org.app1.models.Book;
import org.app1.models.Person;
import org.app1.services.AuthorsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    //запрос на получение страницы с определенным автором
    @Transactional
    @GetMapping("/{id}")
    public String authorPage(@PathVariable int id, Model model) {
        Optional<Author> author = authorsService.findById(id);
        if (author.isPresent()) {

            model.addAttribute("author", author.get());
            List<Book> books = author.get().getBooks();
            model.addAttribute("books", books);

            return "authors/author";
        }
        else {
            return "redirect:/authors";
        }

    }

    //запрос на получение страницы добавления книги
    @GetMapping("/new")
    public String newAuthorPage(Model model,
                              @ModelAttribute("author") Author author) {
        return "authors/new-author";
    }

    //запрос на добавление нового автора
    @Transactional
    @PostMapping()
    public String createAuthor(@ModelAttribute("author") @Valid Author author,
                             @NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authors/new-author";
        }
        authorsService.saveAuthor(author);
        return "redirect:/authors";
    }
}
