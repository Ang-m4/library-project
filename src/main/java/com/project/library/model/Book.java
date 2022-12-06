package com.project.library.model;

import java.time.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
@Table(name = "books")
public class Book {
    
    @Id
    @Column(name = "book_id")
    @GeneratedValue
    private Long id;

    @NotBlank(message = "ISBN is mandatory")
    @Pattern(regexp = "^(97(8|9))?[0-9]{9}(X|[0-9])$", message = "ISBN must be a valid ISBN-10 or ISBN-13")
    @Column(name = "book_isbn", unique = true)
    private String isbn;

    @NotBlank(message = "Title is mandatory")
    @Column(name = "book_title")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Title must be alphanumeric with no special characters")
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Author must contain only letters and spaces")
    @Column(name = "book_author")
    private String author;

    @Column(name = "book_publish_date")
    private LocalDate publishDate;

    @Column(name = "book_copies")
    @Builder.Default
    private int copies = 1;

    @Column(name = "book_publish")
    private String publish;

}
