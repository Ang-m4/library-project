package com.project.library.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "subscriptions")
public class Subscription{

    @Id
    @GeneratedValue
    @Column(name = "subscription_id")
    private Long id;
    
    @ManyToOne
    @NotNull(message = "User is mandatory")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @NotNull(message = "Book is mandatory")
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "subscription_given_date")
    @NotNull(message = "Given date is mandatory")
    private LocalDate givenDate;

    @Column(name = "subscription_return_date")
    @NotNull(message = "Return date is mandatory")
    private LocalDate returnDate;

    @Column(name = "subscription_fine")
    private int fine;

}
