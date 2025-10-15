package com.pinnacle.library.model;

import java.util.Objects;

public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publishedDate;

    public Book() {
    }

//    public Book(String title, String author, String isbn, String publishedDate) {
//        this.title = title;
//        this.author = author;
//        this.isbn = isbn;
//        this.publishedDate = publishedDate;
//    }

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

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn) && Objects.equals(publishedDate, book.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isbn, publishedDate);
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}
