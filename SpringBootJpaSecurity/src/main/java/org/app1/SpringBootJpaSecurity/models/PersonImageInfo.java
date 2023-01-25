package org.app1.SpringBootJpaSecurity.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;

@Entity
@Table(name = "images")
public class PersonImageInfo {

    @Value("${upload.path.person-images}")
    private String uploadPath;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private int size;

    @Column(name = "key")
    private String key;

    @Column(name = "upload_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    public PersonImageInfo() {

    }

    public PersonImageInfo(String name, int size, String key, LocalDate uploadDate) {
        this.name = name;
        this.size = size;
        this.key = key;
        this.date = uploadDate;
    }

    public String getImageLink() throws IOException {
        String classPath = new ClassPathResource("").getFile().getAbsolutePath();
        return classPath + uploadPath + "/" + key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
