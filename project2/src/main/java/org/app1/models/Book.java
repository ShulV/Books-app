package org.app1.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Book {
    private int id;
    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
    private String name;
    @NotEmpty(message = "Поле автор не должно быть пустым")
    @Size(min = 2, max = 100, message = "Поле автор должно быть от 2 до 100 символов")
    private String author;
    @NotNull(message = "Дата не должна быть пустой")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public Book(int id, String name, String author, LocalDate date) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.date = date;
    }

    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getYear() {
        return date.getYear();
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
