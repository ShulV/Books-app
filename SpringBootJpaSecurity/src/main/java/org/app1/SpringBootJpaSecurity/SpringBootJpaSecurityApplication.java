package org.app1.SpringBootJpaSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJpaSecurityApplication {

//	@Bean
//	public CommandLineRunner commandLineRunner(PeopleService peopleService, PasswordEncoder encoder) {
//		return args -> {
//			Person p = new Person();
//			p.setLogin("user1");
//			p.setPassHash(encoder.encode("password"));
//			p.setName("Vasya");
//			p.setPatronymic("Vasilich");
//			p.setSurname("Vasilev");
//			p.setBirthday(LocalDate.now());
//			p.setEmail("kek@mail.ru");
//			peopleService.save(p);
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaSecurityApplication.class, args);
	}

}
