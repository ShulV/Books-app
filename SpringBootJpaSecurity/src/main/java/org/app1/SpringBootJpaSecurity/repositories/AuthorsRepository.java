package org.app1.SpringBootJpaSecurity.repositories;

import org.app1.SpringBootJpaSecurity.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Integer> {
}
