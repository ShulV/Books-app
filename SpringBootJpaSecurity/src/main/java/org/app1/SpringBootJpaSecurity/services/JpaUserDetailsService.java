package org.app1.SpringBootJpaSecurity.services;

import org.app1.SpringBootJpaSecurity.models.SecurityUser;
import org.app1.SpringBootJpaSecurity.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public JpaUserDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return peopleRepository
                .findByLogin(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
