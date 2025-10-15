package com.pinnacle_library.service.impl;

import com.pinnacle_library.dto.request.BookRequest;
import com.pinnacle_library.dto.response.BookPageResponse;
import com.pinnacle_library.dto.response.BookResponse;
import com.pinnacle_library.dto.response.ResponseDTO;
import com.pinnacle_library.exception.DuplicateIsbnException;
import com.pinnacle_library.model.Book;
import com.pinnacle_library.repository.BookRepository;
import com.pinnacle_library.service.BookService;
import com.pinnacle_library.utils.BookUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for handling Book-related business logic.
 */
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    private final BookUtils bookUtils;

    public final String MESSAGE = "Book added successfully! ISBN: ";

    public BookServiceImpl(BookRepository bookRepository, BookUtils bookUtils) {
        this.bookRepository = bookRepository;
        this.bookUtils = bookUtils;
    }

    @Transactional
    @Override
    public ResponseDTO addBook(BookRequest bookRequestDTO) {
        log.info("Processing ISBN: {}", bookRequestDTO.getIsbn());
        if (bookUtils.isDuplicateIsbn(bookRequestDTO.getIsbn())) {
            log.warn("Duplicate ISBN found: {}", bookRequestDTO.getIsbn());
            throw new DuplicateIsbnException(bookRequestDTO.getIsbn());
        }
        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setIsbn(bookRequestDTO.getIsbn());
        book.setPublishedDate(bookRequestDTO.getPublishedDate());
        bookRepository.save(book);
        log.info("Book with ISBN: {} added successfully.", bookRequestDTO.getIsbn());
        return new ResponseDTO(MESSAGE + bookRequestDTO.getIsbn());
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<BookResponse> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);


        return book.map(b -> new BookResponse( b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPublishedDate()));
    }

    @Override
    public Optional<Book> updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthor(bookDetails.getAuthor());
                    book.setIsbn(bookDetails.getIsbn());
                    book.setPublishedDate(bookDetails.getPublishedDate());
                    return bookRepository.save(book);
                });
    }

    @Override
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Book> searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllBooks();
        }
        return bookRepository.search(query);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).isPresent();
    }

    @Override
    public BookPageResponse getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        BookPageResponse dto = new BookPageResponse();
        dto.setContent(bookPage.getContent().stream().map(this::toResponse).collect(Collectors.toList()));
        dto.setTotalPages(bookPage.getTotalPages());
        dto.setTotalElements(bookPage.getTotalElements());
        dto.setPageNumber(bookPage.getNumber());
        dto.setPageSize(bookPage.getSize());

        return dto;
    }

    private BookResponse toResponse(Book entity) {
        BookResponse dto = new BookResponse();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setIsbn(entity.getIsbn());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }


}
