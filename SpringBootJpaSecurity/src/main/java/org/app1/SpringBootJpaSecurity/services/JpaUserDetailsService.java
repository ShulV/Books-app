package org.app1.SpringBootJpaSecurity.services;

import org.app1.SpringBootJpaSecurity.models.Person;
import org.app1.SpringBootJpaSecurity.models.SecurityUser;
import org.app1.SpringBootJpaSecurity.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;
    private final PeopleService peopleService;

    @Autowired
    public JpaUserDetailsService(PeopleRepository peopleRepository, PeopleService peopleService) {
        this.peopleRepository = peopleRepository;
        this.peopleService = peopleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return peopleRepository
                .findByLogin(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

    public Optional<Person> getAuthenticatedUser() {
       Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
       return peopleRepository.findByLogin(authentication.getName());
    }
}
