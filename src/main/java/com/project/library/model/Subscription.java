package com.project.library.model;
import java.time.LocalDate;
import java.util.List;

public class Subscription{

    private Long id;
    private Reader reader;
    private List<Book> books;
    private LocalDate givenDate;
    private LocalDate returnDate;

    public Subscription() {
    }   

    public Subscription(long id, Reader reader, List<Book> books, LocalDate requestDate, LocalDate givenDate, LocalDate returnDate) {
        this.id = id;
        this.reader = reader;
        this.books = books;
        this.givenDate = givenDate;
        this.returnDate = returnDate;
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

    public LocalDate getGivenDate() {
        return givenDate;
    }
    public void setGivenDate(LocalDate givenDate) {
        this.givenDate = givenDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
     
    
}
