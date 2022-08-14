package com.project.library.model;

import java.time.LocalDate;
import java.util.List;

public class Subscription extends Order {

    private LocalDate givenDate;
    private LocalDate returnDate;

    public Subscription() {
    }   

    public Subscription(long id, Reader reader, List<Book> books, LocalDate requestDate, LocalDate givenDate, LocalDate returnDate) {
        super(id, reader, books, requestDate);
        this.givenDate = givenDate;
        this.returnDate = returnDate;
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
