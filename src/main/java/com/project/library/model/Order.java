package com.project.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull(message = "User is mandatory")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @NotNull(message = "Book is mandatory")
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "order_request_date")
    @NotBlank(message = "Request date is mandatory")
    private LocalDate requestDate;

}
