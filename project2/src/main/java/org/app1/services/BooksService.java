package org.app1.services;

import org.app1.models.Book;
import org.app1.models.Person;
import org.app1.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    public Optional<Book> findById(int id) {
        return booksRepository.findById(id);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book updatedBook, int id) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void deleteById(int id) {
        booksRepository.deleteById(id);
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
}