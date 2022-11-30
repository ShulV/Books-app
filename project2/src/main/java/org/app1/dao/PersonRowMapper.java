package org.app1.dao;

import org.app1.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setName(rs.getString("name"));
        person.setPatronymic(rs.getString("patronymic"));
        person.setSurname(rs.getString("surname"));
        person.setBirthday(rs.getObject("birthday", LocalDate.class));
        person.setEmail(rs.getString("email"));
        return person;
    }
}
