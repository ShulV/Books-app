package org.app1.SpringBootJpaSecurity.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "people")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Имя не должен быть пустым")
    @Size(min = 2, max = 30, message = "Длина имени должна быть от 2 до 30 символов")
    private String name;

    @Column(name = "patronymic")
    @NotEmpty(message = "Отчество не должно быть пустым")
    @Size(min = 2, max = 30, message = "Длина отчества должна быть от 2 до 30 символов")
    private String patronymic;

    @Column(name = "surname")
    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Size(min = 2, max = 30, message = "Длина фамилии должна быть от 2 до 30 символов")
    private String surname;

    @Column(name = "birthday")
    @NotNull(message = "Дата не должна быть пустой")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    @Column(name = "email")
    @NotNull(message = "Email не должен быть пустой")
    @Email(message = "Email должен быть валидным")
    private String email;

    @Column(name = "login")
    @NotNull(message = "Логин не должен быть пустой")
    @Size(min = 3, max = 50, message = "Длина логина должна быть от 3 до 50 символов")
    private String login;

    @Transient
    @NotNull(message = "Пароль не должен быть пустой")
    private String password;


    @Column(name = "pass_hash")
    @Size(min = 6, max = 256, message = "Длина пароля должна быть от 6 до 256 символов")

    private String passHash;

    @Column(name = "role")
    private String role;

//    @Transient
//    @NotNull(message = "Пароль не должен быть пустой")
//    @Size(min = 6, max = 50, message = "Длина пароля должна быть от 6 до 50 символов")
//    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Book> books;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private List<PersonImageInfo> personImageInfos;

    public Person() {
    }

    public Person(int id, String name, String patronymic, String surname,
                  LocalDate birthday, String email,
                  String login, String passHash) {
        this.id = id;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.birthday = birthday;
        this.email = email;
        this.login = login;
        this.passHash = passHash;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PersonImageInfo> getPersonImages() {
        return personImageInfos;
    }

    public PersonImageInfo getLastPersonImage() {
        if (personImageInfos.isEmpty()) {
            return null;
        } else {
            return personImageInfos.get(personImageInfos.size() - 1);
        }
    }

    public void setPersonImages(List<PersonImageInfo> personImageInfos) {
        this.personImageInfos = personImageInfos;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person{" +
                "surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", passHash='" + passHash + '\'' +
                '}';
    }
}
