package com.pinnacle_library.service;

import com.pinnacle_library.dto.request.BookRequest;
import com.pinnacle_library.dto.response.BookPageResponse;
import com.pinnacle_library.dto.response.BookResponse;
import com.pinnacle_library.dto.response.ResponseDTO;
import com.pinnacle_library.model.Book;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookService {

    ResponseDTO addBook(BookRequest bookRequestDTO);

    List<Book> getAllBooks();

    Optional<BookResponse> getBookById(Long id);

    Optional<Book> updateBook(Long id, Book bookDetails);

    boolean deleteBook(Long id);

    List<Book> searchBooks(@Param("query") String query);

    boolean existsByIsbn(String isbn);

    BookPageResponse getAllBooks(int page, int size);

}
