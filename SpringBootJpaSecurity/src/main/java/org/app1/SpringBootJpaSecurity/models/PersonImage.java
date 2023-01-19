package org.app1.SpringBootJpaSecurity.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Table(name = "images")
public class PersonImage {

    @Value("${upload.path.person-images}")
    private String uploadPath;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private int size;

    @Column(name = "hashed_name")
    private String hashedName;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    public PersonImage() {

    }

    public PersonImage(String name, int size, String hashedName) {
        this.name = name;
        this.size = size;
        this.hashedName = hashedName;
    }

    public String getImageLink() throws IOException {
        String classPath = new ClassPathResource("").getFile().getAbsolutePath();
        return classPath + uploadPath + "/" + hashedName;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getHashedName() {
        return hashedName;
    }

    public void setHashedName(String hashedName) {
        this.hashedName = hashedName;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}
