package org.app1.dao;

import org.app1.models.Book;
import org.app1.models.Person;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Transactional(readOnly = true)
@Component
public class BookDAO {
    private final EntityManager entityManager;

    @Autowired
    public BookDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> getBooksWithAuthors() {
        Session session = entityManager.unwrap(Session.class);
        System.out.println("DAO");
        List<Book> books = session.createQuery("select b from Book b left join fetch b.authors", Book.class)
                .getResultList();
        books = new ArrayList<>(new HashSet<>(books));
        return books;
    }
}
