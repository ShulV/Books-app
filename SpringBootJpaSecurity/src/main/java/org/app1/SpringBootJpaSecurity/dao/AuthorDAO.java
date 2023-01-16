package org.app1.SpringBootJpaSecurity.dao;


import org.app1.SpringBootJpaSecurity.models.Author;
import org.app1.SpringBootJpaSecurity.models.Book;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;


@Component
public class AuthorDAO {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public AuthorDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Author> getAuthorsByBook(@NotNull Book book) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "select a from Author as a left join a.books as b where b.id = :book_id";
        Query query = session.createQuery(hql);
        query.setParameter("book_id", book.getId());
        return query.getResultList();
    }

}
