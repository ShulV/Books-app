package org.app1.services;

import org.app1.dao.BookDAO;
import org.app1.models.Author;
import org.app1.models.Book;
import org.app1.models.Person;
import org.app1.repositories.BooksRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.app1.util.StringParser.parseStrNumSeqToList;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BookDAO bookDAO;
    private final BooksRepository booksRepository;
    private final AuthorsService authorsService;

    @Autowired
    public BooksService(BookDAO bookDAO, BooksRepository booksRepository, AuthorsService authorsService) {
        this.bookDAO = bookDAO;
        this.booksRepository = booksRepository;
        this.authorsService = authorsService;
    }

    public List<Book> findAll(boolean sort_by_year) {
        if (sort_by_year) {
            return booksRepository.findAll(Sort.by("date"));
        }
        return bookDAO.getBooksWithAuthors();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sort_by_year) {
        if (sort_by_year) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("date"))).getContent();
        }
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }


    public Optional<Book> findById(int id) {
        return booksRepository.findById(id);
    }

    @Transactional
    public void save(Book book) {
        book.setAuthors(Collections.emptyList());
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book updatedBook, int id) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void deleteById(int id) {
        //отвязали данную книгу от всех авторов
        Optional<Book> book = booksRepository.findById(id);
        if(book.isPresent()){
            authorsService.unlinkBookFromAuthors(book.get());
            //удалили книгу
            booksRepository.deleteById(id);
        }

    }

    public Optional<Person> getOwner(int id) {
        return findById(id).map(Book::getOwner);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        findById(id).ifPresent(book -> {
            book.setOwner(selectedPerson);
        });
    }

    @Transactional
    public void release(int id) {
        findById(id).ifPresent(book -> {
            book.setOwner(null);
        });
    }

    public List<Book> searchByTitle(String query) {
        return booksRepository.findByNameStartingWith(query);
    }

    @Transactional
    public void saveWithAuthorsIdString(Book book) {
        booksRepository.save(book);

        //Парсинг строки, пришедшей с multiple select(option) в массив чисел
        int[] authorIdList = parseStrNumSeqToList(book.getAuthorsIdString());
        List<Author> authors = new ArrayList<>();

        //Поиск всех авторов по id
        for(int i=0; i< authorIdList.length; i++) {
            Author author = authorsService.getAuthorById(authorIdList[i]).get();
            author.addBook(book);
            authors.add(author);

        }
        Hibernate.initialize(book);

        for (Author a: authors
             ) {
//            a.setDeathDate(LocalDate.parse("14-04-1233"));
            System.out.println(a);
        }
    }
}
