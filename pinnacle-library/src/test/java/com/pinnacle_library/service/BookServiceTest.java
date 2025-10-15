package com.pinnacle_library.service;

import com.pinnacle_library.dto.request.BookRequest;
import com.pinnacle_library.dto.response.BookPageResponse;
import com.pinnacle_library.dto.response.BookResponse;
import com.pinnacle_library.model.Book;
import com.pinnacle_library.repository.BookRepository;
import com.pinnacle_library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookRequest testRequest;
    private Book testBook;

    @BeforeEach
    void setUp() {
        testRequest = new BookRequest();
        testRequest.setTitle("Test Title");
        testRequest.setAuthor("Test Author");
        testRequest.setIsbn("1234567890");
        testRequest.setPublishedDate(LocalDate.now());

        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Title");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("1234567890");
        testBook.setPublishedDate(LocalDate.now());
    }

    @Test
    void getAllBooks_shouldReturnPaginatedResponses() {
        List<Book> books = Collections.singletonList(testBook);
        Page<Book> page = new PageImpl<>(books, PageRequest.of(0, 10), 1);
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(page);

        BookPageResponse result = bookService.getAllBooks(0, 10);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalPages());
        verify(bookRepository, times(1)).findAll(any(Pageable.class));
    }


}
