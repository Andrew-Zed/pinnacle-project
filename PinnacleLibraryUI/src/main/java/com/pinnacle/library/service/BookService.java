package com.pinnacle.library.service;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.pinnacle.library.adapters.*;
import com.pinnacle.library.dto.BookPageResponse;
import com.pinnacle.library.dto.BookRequest;
import com.pinnacle.library.dto.BookResponse;
import com.pinnacle.library.dto.ResponseDTO;
import com.pinnacle.library.model.Book;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    private static final String DEFAULT_URL = "http://localhost:8080";
    private static final String API_PATH = "/api/v1/books";
    private final Gson gson;
    private final HttpClient httpClient;
    private final String baseUrl;

    public BookService() {
        this.httpClient = HttpClients.createDefault();
        this.baseUrl = getBackendUrl();

        this.gson = new com.google.gson.GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, typeOfT, ctx) ->
                                LocalDate.parse(json.getAsString()))
                .registerTypeAdapter(BookResponse.class, new BookResponseAdapter())
                .registerTypeAdapter(BookPageResponse.class, new BookPageResponseAdapter())
                .registerTypeAdapter(BookRequest.class, new BookRequestAdapter())
                .registerTypeAdapter(ResponseDTO.class, new ResponseDTOAdapter())
                .registerTypeAdapter(Book.class, new BookAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .serializeNulls()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .create();
    }

    /**
     * Get the backend URL from environment or system properties.
     */
    private String getBackendUrl() {
        String systemUrl = System.getProperty("backend.url");
        if (systemUrl != null && !systemUrl.trim().isEmpty()) {
            System.out.println("Using backend URL from system property: " + systemUrl);
            return systemUrl + API_PATH;
        }

        String envUrl = System.getenv("BACKEND_URL");
        if (envUrl != null && !envUrl.trim().isEmpty()) {
            System.out.println("Using backend URL from environment variable: " + envUrl);
            return envUrl + API_PATH;
        }

        System.out.println("Using default backend URL: " + DEFAULT_URL);
        return DEFAULT_URL + API_PATH;
    }

    /**
     * Fetch all books with pagination from the backend.
     * GET /api/v1/books/get-all-books?page=0&size=10
     */
    public BookPageResponse getAllBooks(int page, int size) throws Exception {
        String url = baseUrl + "/get-all-books?page=" + page + "&size=" + size;
        HttpGet request = new HttpGet(url);
        request.addHeader("Content-Type", "application/json");

        return httpClient.execute(request, response -> {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = new String(entity.getContent().readAllBytes(), StandardCharsets.UTF_8);
                return gson.fromJson(jsonResponse, BookPageResponse.class);
            }
            throw new IOException("Failed to fetch books: HTTP " + statusCode);
        });
    }

    /**
     * Fetch all books (without pagination) - loads all pages.
     */
    public List<BookResponse> getAllBooksNoPagination() throws Exception {
        List<BookResponse> allBooks = new ArrayList<>();
        int page = 0;
        int size = 100;
        BookPageResponse pageResponse;

        do {
            pageResponse = getAllBooks(page, size);
            allBooks.addAll(pageResponse.getContent());
            page++;
        } while (page < pageResponse.getTotalPages());

        return allBooks;
    }

    /**
     * Get a single book by ID.
     * GET /api/v1/books/get-book/{id}
     */
    public BookResponse getBookById(Long id) throws Exception {
        String url = baseUrl + "/get-book/" + id;
        HttpGet request = new HttpGet(url);
        request.addHeader("Content-Type", "application/json");

        return httpClient.execute(request, response -> {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = new String(entity.getContent().readAllBytes(), StandardCharsets.UTF_8);
                return gson.fromJson(jsonResponse, BookResponse.class);
            }
            throw new IOException("Failed to fetch book: HTTP " + statusCode);
        });
    }

    /**
     * Add a new book to the backend.
     * POST /api/v1/books/add-book
     */
    public ResponseDTO addBook(BookRequest bookRequest) throws Exception {
        String url = baseUrl + "/add-book";
        HttpPost request = new HttpPost(url);
        request.addHeader("Content-Type", "application/json");

        String json = gson.toJson(bookRequest);
        request.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

        return httpClient.execute(request, response -> {
            int statusCode = response.getCode();
            if (statusCode == 201 || statusCode == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = new String(entity.getContent().readAllBytes(), StandardCharsets.UTF_8);
                return gson.fromJson(jsonResponse, ResponseDTO.class);
            }
            throw new IOException("Failed to add book: HTTP " + statusCode);
        });
    }

    /**
     * Update an existing book on the backend.
     * PUT /api/v1/books/update/{id}
     */
    public Book updateBook(Long id, Book book) throws Exception {
        String url = baseUrl + "/update/" + id;
        HttpPut request = new HttpPut(url);
        request.addHeader("Content-Type", "application/json");

        String json = gson.toJson(book);
        request.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

        return httpClient.execute(request, response -> {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = new String(entity.getContent().readAllBytes(), StandardCharsets.UTF_8);
                return gson.fromJson(jsonResponse, Book.class);
            }
            throw new IOException("Failed to update book: HTTP " + statusCode);
        });
    }

    /**
     * Delete a book from the backend.
     * DELETE /api/v1/books/delete/{id}
     */
    public boolean deleteBook(Long id) throws Exception {
        String url = baseUrl + "/delete/" + id;
        HttpDelete request = new HttpDelete(url);
        request.addHeader("Content-Type", "application/json");

        return httpClient.execute(request, response -> {
            int statusCode = response.getCode();
            return statusCode == 204 || statusCode == 200;
        });
    }

    /**
     * Search for books by title or author.
     * GET /api/v1/books/search?query=xxx
     */
    public List<Book> searchBooks(String query) throws Exception {
        if (query == null || query.trim().isEmpty()) {
            List<BookResponse> responses = getAllBooksNoPagination();
            return convertToBookList(responses);
        }

        String url = baseUrl + "/search?query=" + java.net.URLEncoder.encode(query, StandardCharsets.UTF_8);
        HttpGet request = new HttpGet(url);
        request.addHeader("Content-Type", "application/json");

        return httpClient.execute(request, response -> {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = new String(entity.getContent().readAllBytes(), StandardCharsets.UTF_8);

                List<BookResponse> responses = gson.fromJson(jsonResponse, new TypeToken<List<BookResponse>>(){}.getType());
                return convertToBookList(responses);
            }
            return new ArrayList<>();
        });
    }


    /**
     * Check if a book exists by ISBN.
     * GET /api/v1/books/exists/{isbn}
     */
    public boolean existsByIsbn(String isbn) throws Exception {
        String url = baseUrl + "/exists/" + isbn;
        HttpGet request = new HttpGet(url);
        request.addHeader("Content-Type", "application/json");

        return httpClient.execute(request, response -> {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = new String(entity.getContent().readAllBytes(), StandardCharsets.UTF_8);
                return Boolean.parseBoolean(jsonResponse);
            }
            return false;
        });
    }

    /**
     * Helper method to convert BookResponse to Book.
     */
    private Book convertToBook(BookResponse response) {
        Book book = new Book();
        book.setId(response.getId());
        book.setTitle(response.getTitle());
        book.setAuthor(response.getAuthor());
        book.setIsbn(response.getIsbn());
        book.setPublishedDate(response.getPublishedDate() != null ? response.getPublishedDate().toString() : null);
        return book;
    }

    /**
     * Helper method to convert List<BookResponse> to List<Book>.
     */
    private List<Book> convertToBookList(List<BookResponse> responses) {
        List<Book> books = new ArrayList<>();
        for (BookResponse response : responses) {
            books.add(convertToBook(response));
        }
        return books;
    }

}
