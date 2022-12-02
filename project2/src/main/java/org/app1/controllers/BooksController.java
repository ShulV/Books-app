package org.app1.controllers;


import org.app1.dao.PersonDAO;
import org.app1.models.Book;
import org.app1.models.Person;
import org.app1.services.BooksService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BooksService booksService, PersonDAO personDAO) {
        this.booksService = booksService;
        this.personDAO = personDAO;
    }

    //запрос на получение страницы со списком всех книг
    @GetMapping()
    public String allBooksPage(@NotNull Model model) {
        model.addAttribute("books", booksService.getAllBooks());
        return "books/all-books";
    }
    //запрос на получение страницы с определенной книгой
    @GetMapping("/{id}")
    public String bookPage(@PathVariable int id, Model model, @ModelAttribute("person") Person person) {
        Optional<Book> book = booksService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            Optional<Person> personOwner = booksService.getOwner(id);
            if (personOwner.isPresent()) {
                model.addAttribute("person_owner", personOwner.get());
            }
            else {
                model.addAttribute("people", personDAO.index());
            }
            return "books/book";
        }
        else {
            return "redirect:/books";
        }

    }
    //запрос на получение страницы добавления книги
    @GetMapping("/new")
    public String newBookPage(@ModelAttribute("book") Book book) {
//        model.addAttribute("book", new Book());
        return "books/new-book";
    }
    //запрос на добавление новой книги
    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             @NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new-book";

        booksService.save(book);
        return "redirect:/books";
    }
    //запрос на получение страницы изменения книги
    @GetMapping("/{id}/edit")
    public String editBookPage(@PathVariable int id, Model model) {

        Optional<Book> selectedBook = booksService.findById(id);
        if (selectedBook.isPresent()) {
            model.addAttribute("book", selectedBook.get());
            return "/books/edit-book";
        }
        return "redirect:/books";
    }

    //запрос на редактирование данных книги
    @PatchMapping("/{id}")
    public String edit(@PathVariable int id, @ModelAttribute("book") @Valid Book updatedBook,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/edit-book";

        booksService.update(updatedBook, id);
        return "redirect:/books/" + id;
    }

    //запрос на удаление книги
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        booksService.deleteById(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable int id, @ModelAttribute("person") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }
}
