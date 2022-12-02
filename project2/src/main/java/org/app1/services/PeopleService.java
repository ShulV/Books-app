package org.app1.services;

import org.app1.models.Book;
import org.app1.models.Person;
import org.app1.repositories.BooksRepository;
import org.app1.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    public Optional<Person> getPersonById(int id) {
        return peopleRepository.findById(id);
    }

    public Optional<Person> getPersonByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }

    public List<Book> getBooksByPerson(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        }
        return Collections.emptyList();
    }

    @Transactional
    public void deleteById(int id) {
        System.out.println("dele");
        peopleRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Person updatedPerson, int id) {
        System.out.println("upda");
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }


}
