package org.app1.SpringBootJpaSecurity;

import org.app1.SpringBootJpaSecurity.services.PeopleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringBootJpaSecurityApplication {

	@Bean
	public CommandLineRunner commandLineRunner(PeopleService peopleService, PasswordEncoder encoder) {
		return args -> {
//			System.out.println(encoder.encode("password"));
//			System.out.println(encoder.encode("password"));
//			System.out.println(encoder.encode("password"));
//			System.out.println(encoder.encode("password"));
			
//			Person p = new Person();
//			p.setLogin("user1");
//			p.setPassHash(encoder.encode("password"));
//			p.setName("Vasya");
//			p.setPatronymic("Vasilich");
//			p.setSurname("Vasilev");
//			p.setBirthday(LocalDate.now());
//			p.setEmail("kek@mail.ru");
//			peopleService.save(p);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaSecurityApplication.class, args);
	}

}
