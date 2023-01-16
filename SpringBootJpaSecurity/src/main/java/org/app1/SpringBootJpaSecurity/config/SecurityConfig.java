package org.app1.SpringBootJpaSecurity.config;

import org.app1.SpringBootJpaSecurity.services.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JpaUserDetailsService JpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService myUserDetailsService) {
        this.JpaUserDetailsService = myUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //                .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
                .csrf().disable()
                .authorizeRequests()
                //пускать неаутентифицир. пользователя на эти страницы
                .antMatchers("/auth/login", "/error").permitAll()
                //                        .mvcMatchers("/people/**").permitAll()
                //для всех остальных запросов пользователь должен быть аутентифицирован
                .anyRequest().authenticated()
                .and()
                //кастомная форма аутентификации
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/books/", true)
                .failureUrl("/auth/login?error")
                .and()

                .userDetailsService(JpaUserDetailsService)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

