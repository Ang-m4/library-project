package com.project.library.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class Subscription{

    @Id
    @GeneratedValue
    @Column(name = "subscription_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "book_isbn")
    private Book book;

    @Column(name = "subscription_given_date")
    private LocalDate givenDate;

    @Column(name = "subscription_return_date")
    private LocalDate returnDate;

    @Column(name = "subscription_fine")
    private int fine;

    public Subscription() {
    }   

    public Subscription(long id, Reader reader, Book book, LocalDate givenDate, LocalDate returnDate, int fine) {
        this.id = id;
        this.reader = reader;
        this.book = book;
        this.givenDate = givenDate;
        this.returnDate = returnDate;
        this.fine = fine;
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
    
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
     
    
}
