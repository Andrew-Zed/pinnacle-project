package com.pinnacle.library.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class BookResponse {

    @SerializedName("id")
    @Expose
    public Long id;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("author")
    @Expose
    public String author;

    @SerializedName("isbn")
    @Expose
    public String isbn;

    @SerializedName("publishedDate")
    @Expose
    public LocalDate publishedDate;

    public BookResponse() {
    }

    public BookResponse(Long id, String title, String author,
                        String isbn, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
}
