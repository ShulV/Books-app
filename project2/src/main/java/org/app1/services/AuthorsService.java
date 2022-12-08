package org.app1.services;

import org.app1.models.Author;
import org.app1.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorsService {

    private final AuthorsRepository authorsRepository;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<Author> getAllAuthors() {
        return authorsRepository.findAll();
    }

    public Optional<Author> getAuthorById(int id) {
       return authorsRepository.findById(id);
    }

}
