package org.app1.dao;

import org.app1.models.Book;
import org.app1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //получить список всех людей
    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM People", new PersonRowMapper());
    }

    //добавить человека
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO public.people(name, patronymic, surname, birthday, email)" +
                        "VALUES (?, ?, ?, ?, ?);",
                person.getName(),
                person.getPatronymic(),
                person.getSurname(),
                person.getBirthday(),
                person.getEmail());
    }

    //получить человека по id
    public Optional<Person> getPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM People WHERE id=?;",
                new PersonRowMapper(), new Object[]{id}).stream().findAny();
    }

    //получить человека по id
    public Optional<Person> getPersonByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM People WHERE email=?;",
                new PersonRowMapper(), new Object[]{email}).stream().findAny();
    }

    //получить список книг, взятых определенным человеком
    public List<Book> getBooksByPerson(int id) {
        return jdbcTemplate.query("select * from Books where person_id = ?", new BookRowMapper(), id);
    }

    //удалить человека по id
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM People WHERE id = ?;", id);
    }

    //редактировать данные о человеке
    public void update(Person updatedPerson, int id) {
        jdbcTemplate.update("update people set " +
                "name = ?, patronymic = ?, surname = ?, birthday = ?, email = ? where id = ?;",
                updatedPerson.getName(),
                updatedPerson.getPatronymic(),
                updatedPerson.getSurname(),
                updatedPerson.getBirthday(),
                updatedPerson.getEmail(),
                id);
    }
}
