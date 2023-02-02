package org.app1.SpringBootJpaSecurity.controllers;


import org.app1.SpringBootJpaSecurity.models.Book;
import org.app1.SpringBootJpaSecurity.models.Person;
import org.app1.SpringBootJpaSecurity.services.AuthorsService;
import org.app1.SpringBootJpaSecurity.services.BooksService;
import org.app1.SpringBootJpaSecurity.services.JpaUserDetailsService;
import org.app1.SpringBootJpaSecurity.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final AuthorsService authorsService;

    private final JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, AuthorsService authorsService, JpaUserDetailsService jpaUserDetailsService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.authorsService = authorsService;
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    //запрос на получение страницы со списком всех книг
    @GetMapping()
    public String allBooksPage(@NotNull Model model,
                               @RequestParam(name = "page", required = false) Integer page,
                               @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                               @RequestParam(name = "sort_by_date", required = false) boolean sortByDate) {
        List<Book> books = new ArrayList<>();
        if (page == null || booksPerPage == null)
            books = booksService.findAll(sortByDate);

        else
            books = booksService.findWithPagination(page, booksPerPage, sortByDate);
        model.addAttribute("books", books);

        Optional<Person> authUser = jpaUserDetailsService.getAuthenticatedUser();
        authUser.ifPresent(value -> model.addAttribute("user", value));
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
                model.addAttribute("people", peopleService.getAllPeople());
            }

            Optional<Person> authUser = jpaUserDetailsService.getAuthenticatedUser();
            authUser.ifPresent(value -> model.addAttribute("user", value));
            return "books/book";
        }
        else {
            return "redirect:/books";
        }

    }
    //запрос на получение страницы добавления книги
    @GetMapping("/new")
    public String newBookPage(Model model,
                              @ModelAttribute("book") Book book) {
        model.addAttribute("authors", authorsService.getAllAuthors());

        Optional<Person> authUser = jpaUserDetailsService.getAuthenticatedUser();
        authUser.ifPresent(value -> model.addAttribute("user", value));
        return "books/new-book";
    }

    //запрос на добавление новой книги
    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             @NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new-book";
        }
        booksService.saveWithAuthorsIdString(book);
        return "redirect:/books";
    }
    //запрос на получение страницы изменения книги
    @GetMapping("/{id}/edit")
    public String editBookPage(@PathVariable int id, Model model) {

        Optional<Book> selectedBook = booksService.findById(id);
        if (selectedBook.isPresent()) {
            model.addAttribute("authors", authorsService.getAllAuthors());
            model.addAttribute("book", selectedBook.get());

            Optional<Person> authUser = jpaUserDetailsService.getAuthenticatedUser();
            authUser.ifPresent(value -> model.addAttribute("user", value));
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

        booksService.unlinkAllAuthors(updatedBook.getId());
        booksService.saveWithAuthorsIdString(updatedBook);
        return "redirect:/books/" + id;
    }

    //запрос на удаление книги
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        booksService.deleteById(id);
        return "redirect:/books";
    }

    //присваивание книги
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable int id, @ModelAttribute("person") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    //возврат книги
    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }


    @GetMapping("/search")
    public String getSearchPage(Model model) {
        Optional<Person> authUser = jpaUserDetailsService.getAuthenticatedUser();
        authUser.ifPresent(value -> model.addAttribute("user", value));
        return "books/search";
    }

    @PostMapping("/search")
    public String searchBook(Model model,
                                @RequestParam(name = "query") String query) {
        model.addAttribute("books", booksService.searchByTitle(query));
        return "books/search";
    }
}
