package org.app1.SpringBootJpaSecurity;

import org.app1.SpringBootJpaSecurity.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;

@SpringBootApplication
public class SpringBootJpaSecurityApplication {


	@Bean
	public CommandLineRunner commandLineRunner(PeopleService peopleService, PasswordEncoder encoder) {
		return args -> {
//			String path = new ClassPathResource("").getFile().getAbsolutePath();
//			System.out.println(path);

//			String s1 = encoder.encode("password");
//			String s2 = encoder.encode("password");
//			System.out.println("s1: " + s1 + " ; s2: " + s2);
//			System.out.println("matches1: " + encoder.matches("password", s1));
//			System.out.println("matches2: " + encoder.matches("password", s2));


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
