package com.pinnacle_library.utils;

import com.pinnacle_library.repository.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class BookUtils {

    private final BookRepository bookRepository;

    public BookUtils(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Check if a book with the given ISBN already exists in the database.
     */
    public boolean isDuplicateIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

}
