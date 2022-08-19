package com.project.library.model;

import java.util.List;
import java.time.LocalDate;

public class Order {

    private long id;
    private Reader reader;
    private List<Book> books;
    private LocalDate requestDate;

    public Order() {
    }

    public Order(long id, Reader reader, List<Book> books, LocalDate requestDate) {
        this.id = id;
        this.reader = reader;
        this.books = books;
        this.requestDate = requestDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

}
