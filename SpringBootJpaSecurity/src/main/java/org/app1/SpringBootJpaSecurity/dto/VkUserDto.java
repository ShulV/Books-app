package org.app1.SpringBootJpaSecurity.dto;

public class VkUserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String books;

    public VkUserDto() {
    }

    public VkUserDto(Long id, String firstName, String lastName, String books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }
}
