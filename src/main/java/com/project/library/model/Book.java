package com.project.library.model;

import java.time.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @NotBlank
    @Column(name = "book_isbn")
    private Long isbn;
    @NotBlank
    @Column(name = "book_name")
    private String name;
    @NotBlank
    @Column(name = "book_author")
    private String author;
    @NotBlank
    @Column(name = "book_publish_date")
    private LocalDate publishDate;
    @NotBlank
    @Column(name = "book_copies")
    private int copies;
    @NotBlank
    @Column(name = "book_publish")
    private String publish;

    public Book() {
    }

    public Book(long isbn, String name, String author, LocalDate publishDate, int copies, String publish) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
        this.copies = copies;
        this.publish = publish;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
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

    public LocalDate getpublishDate() {
        return publishDate;
    }

    public void setpublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

}
