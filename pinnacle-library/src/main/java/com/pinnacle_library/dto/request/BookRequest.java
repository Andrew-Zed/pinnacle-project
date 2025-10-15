package com.pinnacle_library.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class BookRequest {

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Author is required")
    private String author;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "ISBN is required")
    private String isbn;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    public BookRequest() {
    }

    public BookRequest(String title, String author, String isbn, LocalDate publishedDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
    }

    public @NotBlank(message = "Title is required") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title is required") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Author is required") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank(message = "Author is required") String author) {
        this.author = author;
    }

    public @NotBlank(message = "ISBN is required") String getIsbn() {
        return isbn;
    }

    public void setIsbn(@NotBlank(message = "ISBN is required") String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
}
