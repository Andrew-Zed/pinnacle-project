package com.pinnacle_library.controller;

import com.pinnacle_library.dto.request.BookRequest;
import com.pinnacle_library.dto.response.BookPageResponse;
import com.pinnacle_library.dto.response.BookResponse;
import com.pinnacle_library.dto.response.ResponseDTO;
import com.pinnacle_library.model.Book;
import com.pinnacle_library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST API Controller for Book management.
 * Handles all HTTP requests for CRUD operations.
 */
@RestController
@RequestMapping("/api/v1/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * POST /api/books - Add a new book.
     */
    @PostMapping("/add-book")
    public ResponseEntity<ResponseDTO> addBook(@Valid @RequestBody BookRequest bookRequest) {
        ResponseDTO response = bookService.addBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Retrieve a book by its ID.
     */
    @GetMapping("/get-book/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        Optional<BookResponse> book = bookService.getBookById(id);
        return book.map(b -> new ResponseEntity<>(b, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update an existing book's details.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> updatedBook = bookService.updateBook(id, bookDetails);
        return updatedBook.map(b -> new ResponseEntity<>(b, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Delete a book by its ID.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBook(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Search for books by title or author.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
        try {
            List<Book> books = bookService.searchBooks(query);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Check if a book exists by ISBN.
     */
    @GetMapping("/exists/{isbn}")
    public ResponseEntity<Boolean> existsByIsbn(@PathVariable String isbn) {
        boolean exists = bookService.existsByIsbn(isbn);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/get-all-books")
    public ResponseEntity<BookPageResponse> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.getAllBooks(page, size));
    }

}
