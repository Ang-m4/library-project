package com.project.library.model;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long isbn;
    private String name;
    private String author;
    private LocalDate publishDate;
    private int copies;
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
