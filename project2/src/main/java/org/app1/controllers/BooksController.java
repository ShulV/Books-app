package org.app1.controllers;


import org.app1.dao.BookDAO;
import org.app1.dao.PersonDAO;
import org.app1.models.Book;
import org.app1.models.Person;
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

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDao, PersonDAO personDAO) {
        this.bookDAO = bookDao;
        this.personDAO = personDAO;
    }

    //запрос на получение страницы со списком всех книг
    @GetMapping()
    public String allBooksPage(@NotNull Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/all-books";
    }
    //запрос на получение страницы с определенной книгой
    @GetMapping("/{id}")
    public String bookPage(@PathVariable int id, Model model, @ModelAttribute("person") Person person) {
        Optional<Book> book = bookDAO.getBook(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            Optional<Person> personOwner = bookDAO.getBookOwner(id);
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

        bookDAO.save(book);
        return "redirect:/books";
    }
    //запрос на получение страницы изменения книги
    @GetMapping("/{id}/edit")
    public String editBookPage(@PathVariable int id, Model model) {

        Optional<Book> selectedBook = bookDAO.getBook(id);
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

        bookDAO.update(updatedBook, id);
        return "redirect:/books/" + id;
    }

    //запрос на удаление книги
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable int id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }
}
