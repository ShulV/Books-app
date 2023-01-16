package org.app1.SpringBootJpaSecurity.controllers;

import org.app1.SpringBootJpaSecurity.models.Author;
import org.app1.SpringBootJpaSecurity.models.Book;
import org.app1.SpringBootJpaSecurity.services.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/authors/")
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
    @GetMapping("/{id}/")
    public String authorPage(@PathVariable int id, Model model) {
        Optional<Author> author = authorsService.findById(id);
        if (author.isPresent()) {

            model.addAttribute("author", author.get());
            List<Book> books = author.get().getBooks();
            if (!books.isEmpty()) {
                model.addAttribute("books", books);
            }


            return "authors/author";
        }
        else {
            return "redirect:/authors/";
        }

    }

    //запрос на получение страницы добавления книги
    @GetMapping("/new/")
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
        return "redirect:/authors/";
    }

    //запрос на получение страницы изменения автора
    @GetMapping("/{id}/edit/")
    public String editAuthorPage(@PathVariable int id, Model model) {

        Optional<Author> selectedAuthor = authorsService.getAuthorById(id);
        if (selectedAuthor.isPresent()) {
            model.addAttribute("author", selectedAuthor.get());
            return "/authors/edit-author";
        }
        return "redirect:/authors/";
    }


    //запрос на редактирование данных автора
    @Transactional
    @PatchMapping("/{id}/")
    public String edit(@PathVariable int id, @ModelAttribute("author") @Valid Author updatedAuthor,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "authors/edit-author";
        authorsService.updateAuthor(id, updatedAuthor);
        return "redirect:/authors/" + id + '/';
    }

    //запрос на удаление книги
    @Transactional
    @DeleteMapping("/{id}/")
    public String delete(@PathVariable int id) {
        authorsService.deleteById(id);
        return "redirect:/authors/";
    }
}
