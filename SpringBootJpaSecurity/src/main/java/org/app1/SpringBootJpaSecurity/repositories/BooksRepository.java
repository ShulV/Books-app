package org.app1.SpringBootJpaSecurity.repositories;

import org.app1.SpringBootJpaSecurity.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameStartingWith(String subStr);
}
