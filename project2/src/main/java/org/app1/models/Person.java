package org.app1.models;



import com.fasterxml.jackson.annotation.JsonFormat;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Person {
    private int id;
    @NotEmpty(message = "Имя не должен быть пустым")
    @Size(min = 2, max = 30, message = "Длина имени должна быть от 2 до 30 символов")
    private String name;
    @NotEmpty(message = "Отчество не должно быть пустым")
    @Size(min = 2, max = 30, message = "Длина отчества должна быть от 2 до 30 символов")
    private String patronymic;
    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Size(min = 2, max = 30, message = "Длина фамилии должна быть от 2 до 30 символов")
    private String surname;
    @NotNull(message = "Дата не должна быть пустой")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    @NotNull(message = "Email не должен быть пустой")
    @Email(message = "Email должен быть валидным")
    private String email;

    public Person() {
    }

    public Person(int id, String name, String patronymic, String surname, LocalDate birthday, String email) {
        this.id = id;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.birthday = birthday;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return String.format("%s %s %s", getName(), getPatronymic(), getSurname());
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public int getBirthdayYear() {
        return birthday.getYear();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                '}';
    }
}
