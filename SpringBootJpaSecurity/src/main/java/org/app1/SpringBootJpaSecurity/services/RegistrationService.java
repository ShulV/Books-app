package org.app1.SpringBootJpaSecurity.services;

import org.app1.SpringBootJpaSecurity.models.Person;
import org.app1.SpringBootJpaSecurity.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {

        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassHash(encodedPassword);
        System.out.println(person);
        peopleRepository.save(person);
    }
}
