package org.app1.SpringBootJpaSecurity.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
    private String name;

    @Column(name = "date")
    @NotNull(message = "Дата не должна быть пустой")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column(name = "taking_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateTime;

//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @ManyToMany(mappedBy = "books",
            fetch = FetchType.EAGER)
    private List<Author> authors;

    @Transient
    private String authorsIdString; //for form

    @Transient
    private boolean isOverdue; //просрочено

    public Book(int id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
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

//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }

    public LocalDate getDate() {
        return date;
    }

    public int getYear() {
        return date.getYear();
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getAuthorsIdString() {
        return authorsIdString;
    }

    public void setAuthorsIdString(String authorsIdString) {
        this.authorsIdString = authorsIdString;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(name, book.name) && Objects.equals(date, book.date) && Objects.equals(owner, book.owner) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, owner, authors);
    }
}
