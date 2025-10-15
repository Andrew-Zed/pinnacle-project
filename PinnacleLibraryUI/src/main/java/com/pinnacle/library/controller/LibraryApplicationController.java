package com.pinnacle.library.controller;

import com.pinnacle.library.dto.BookPageResponse;
import com.pinnacle.library.dto.BookRequest;
import com.pinnacle.library.dto.BookResponse;
import com.pinnacle.library.dto.ResponseDTO;
import com.pinnacle.library.model.Book;
import com.pinnacle.library.service.BookService;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


public class LibraryApplicationController {
    private BookService bookService;
    private TableView<BookResponse> tableView;
    private TextField titleField;
    private TextField authorField;
    private TextField isbnField;
    private DatePicker publishedDatePicker;
    private TextField searchField;
    private Label statusLabel;
    private Label paginationLabel;
    private BookResponse selectedBook;

    // Pagination fields
    private int currentPage = 0;
    private int pageSize = 10;
    private int totalPages = 0;
    private long totalElements = 0;
    private Button prevPageBtn;
    private Button nextPageBtn;
    private ComboBox<Integer> pageSizeCombo;

    public LibraryApplicationController() {
        this.bookService = new BookService();
    }

    /**
     * Create the main UI layout.
     */
    public BorderPane createUI() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Top section: Search and controls
        root.setTop(createTopPanel());

        // Center section: Table and form
        root.setCenter(createCenterPanel());

        // Bottom section: Pagination and status
        root.setBottom(createBottomPanel());

        return root;
    }

    /**
     * Create the top panel with search and controls.
     */
    private VBox createTopPanel() {
        VBox topBox = new VBox(10);
        topBox.setPadding(new Insets(10));
        topBox.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

        HBox searchBox = new HBox(10);
        searchBox.setStyle("-fx-alignment: center-left;");

        Label searchLabel = new Label("Search:");
        searchField = new TextField();
        searchField.setPromptText("Search by title or author...");
        searchField.setPrefWidth(300);

        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-font-size: 11; -fx-padding: 5 15 5 15;");
        searchBtn.setOnAction(e -> handleSearch());

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setStyle("-fx-font-size: 11; -fx-padding: 5 15 5 15;");
        refreshBtn.setOnAction(e -> handleRefresh());

        // Page size selector
        Label pageSizeLabel = new Label("Items per page:");
        pageSizeCombo = new ComboBox<>();
        pageSizeCombo.getItems().addAll(5, 10, 20, 50, 100);
        pageSizeCombo.setValue(pageSize);
        pageSizeCombo.setOnAction(e -> {
            pageSize = pageSizeCombo.getValue();
            currentPage = 0;
            loadBooksAsync();
        });

        searchBox.getChildren().addAll(searchLabel, searchField, searchBtn, refreshBtn, pageSizeLabel, pageSizeCombo);
        topBox.getChildren().add(searchBox);

        return topBox;
    }

    /**
     * Create the center panel with table and form.
     */
    private HBox createCenterPanel() {
        HBox centerBox = new HBox(10);
        centerBox.setPadding(new Insets(10));

        // Left: Table
        VBox tableBox = new VBox(10);
        Label tableLabel = new Label("Books:");
        tableLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        tableView = new TableView<>();
        setupTable();
        tableView.setPrefHeight(500);

        tableBox.getChildren().addAll(tableLabel, tableView);
        HBox.setHgrow(tableBox, javafx.scene.layout.Priority.ALWAYS);

        // Right: Form
        VBox formBox = createFormPanel();
        formBox.setPrefWidth(300);

        centerBox.getChildren().addAll(tableBox, formBox);
        return centerBox;
    }

    /**
     * Set up the TableView columns and selection listener.
     */
    private void setupTable() {
        TableColumn<BookResponse, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> {
            Long value = cellData.getValue().getId();
            return new javafx.beans.property.SimpleObjectProperty<>(value);
        });
        idCol.setPrefWidth(50);

        TableColumn<BookResponse, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        titleCol.setPrefWidth(250);

        TableColumn<BookResponse, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAuthor()));
        authorCol.setPrefWidth(200);

        TableColumn<BookResponse, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIsbn()));
        isbnCol.setPrefWidth(150);

        TableColumn<BookResponse, String> dateCol = new TableColumn<>("Published Date");
        dateCol.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getPublishedDate();
            return new javafx.beans.property.SimpleStringProperty(date != null ? date.toString() : "");
        });
        dateCol.setPrefWidth(120);

        tableView.getColumns().addAll(idCol, titleCol, authorCol, isbnCol, dateCol);

        // Selection listener
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                populateForm(newVal);
            }
        });
    }

    /**
     * Create the form panel for adding/editing books.
     */
    private VBox createFormPanel() {
        VBox formBox = new VBox(10);
        formBox.setPadding(new Insets(15));
        formBox.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1 0 0 1; -fx-spacing: 10;");

        Label formTitle = new Label("Book Details");
        formTitle.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        // Title field
        Label titleLabel = new Label("Title:");
        titleField = new TextField();
        titleField.setPromptText("Enter title");

        // Author field
        Label authorLabel = new Label("Author:");
        authorField = new TextField();
        authorField.setPromptText("Enter author");

        // ISBN field
        Label isbnLabel = new Label("ISBN:");
        isbnField = new TextField();
        isbnField.setPromptText("Enter ISBN");

        // Published date field
        Label dateLabel = new Label("Published Date:");
        publishedDatePicker = new DatePicker();
        publishedDatePicker.setStyle("-fx-padding: 5;");

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        Button addBtn = new Button("Add");
        addBtn.setPrefWidth(70);
        addBtn.setOnAction(e -> handleAdd());

        Button updateBtn = new Button("Update");
        updateBtn.setPrefWidth(70);
        updateBtn.setOnAction(e -> handleUpdate());

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefWidth(70);
        deleteBtn.setStyle("-fx-text-fill: white; -fx-background-color: #e74c3c;");
        deleteBtn.setOnAction(e -> handleDelete());

        Button clearBtn = new Button("Clear");
        clearBtn.setPrefWidth(70);
        clearBtn.setOnAction(e -> clearForm());

        buttonBox.getChildren().addAll(addBtn, updateBtn, deleteBtn, clearBtn);

        formBox.getChildren().addAll(
                formTitle,
                new Separator(),
                titleLabel, titleField,
                authorLabel, authorField,
                isbnLabel, isbnField,
                dateLabel, publishedDatePicker,
                new Separator(),
                buttonBox
        );

        return formBox;
    }

    /**
     * Create the bottom panel with pagination controls and status.
     */
    private HBox createBottomPanel() {
        HBox bottomBox = new HBox(20);
        bottomBox.setPadding(new Insets(10));
        bottomBox.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1 0 0 0;");
        bottomBox.setAlignment(Pos.CENTER_LEFT);

        // Status label
        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-text-fill: #27ae60;");

        // Spacer
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Pagination controls
        HBox paginationBox = new HBox(10);
        paginationBox.setAlignment(Pos.CENTER_RIGHT);

        prevPageBtn = new Button("◀ Previous");
        prevPageBtn.setOnAction(e -> handlePreviousPage());
        prevPageBtn.setDisable(true);

        paginationLabel = new Label("Page 1 of 1");
        paginationLabel.setStyle("-fx-font-weight: bold;");

        nextPageBtn = new Button("Next ▶");
        nextPageBtn.setOnAction(e -> handleNextPage());
        nextPageBtn.setDisable(true);

        paginationBox.getChildren().addAll(prevPageBtn, paginationLabel, nextPageBtn);

        bottomBox.getChildren().addAll(statusLabel, spacer, paginationBox);
        return bottomBox;
    }

    /**
     * Initialize the controller by loading books from the backend.
     */
    public void initialize() {
        loadBooksAsync();
    }

    /**
     * Load books from the backend asynchronously with pagination.
     */
    private void loadBooksAsync() {
        new Thread(() -> {
            try {
                BookPageResponse pageResponse = bookService.getAllBooks(currentPage, pageSize);
                Platform.runLater(() -> {
                    tableView.getItems().clear();
                    tableView.getItems().addAll(pageResponse.getContent());

                    // Update pagination info
                    totalPages = pageResponse.getTotalPages();
                    totalElements = pageResponse.getTotalElements();
                    updatePaginationControls();

                    updateStatus("Loaded " + pageResponse.getContent().size() + " of " +
                            totalElements + " books", true);
                });
            } catch (Exception ex) {
                Platform.runLater(() -> updateStatus("Error loading books: " + ex.getMessage(), false));
            }
        }).start();
    }

    /**
     * Update pagination controls based on current page.
     */
    private void updatePaginationControls() {
        prevPageBtn.setDisable(currentPage == 0);
        nextPageBtn.setDisable(currentPage >= totalPages - 1);
        paginationLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);
    }

    /**
     * Handle previous page button.
     */
    private void handlePreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadBooksAsync();
        }
    }

    /**
     * Handle next page button.
     */
    private void handleNextPage() {
        if (currentPage < totalPages - 1) {
            currentPage++;
            loadBooksAsync();
        }
    }

    /**
     * Handle the Add button action.
     */
    private void handleAdd() {
        if (!validateForm()) {
            return;
        }

        BookRequest bookRequest = new BookRequest(
                titleField.getText().trim(),
                authorField.getText().trim(),
                isbnField.getText().trim(),
                publishedDatePicker.getValue()
        );

        new Thread(() -> {
            try {
                // Check if ISBN already exists
                boolean exists = bookService.existsByIsbn(bookRequest.getIsbn());
                if (exists) {
                    Platform.runLater(() -> updateStatus("Error: Book with this ISBN already exists", false));
                    return;
                }

                ResponseDTO response = bookService.addBook(bookRequest);
                Platform.runLater(() -> {
                    clearForm();
                    loadBooksAsync(); // Reload to show new book
                    updateStatus(response.getMessage(), true);
                });
            } catch (Exception ex) {
                Platform.runLater(() -> updateStatus("Error adding book: " + ex.getMessage(), false));
            }
        }).start();
    }

    /**
     * Handle the Update button action.
     */
    private void handleUpdate() {
        if (selectedBook == null) {
            updateStatus("Please select a book to update", false);
            return;
        }

        if (!validateForm()) {
            return;
        }

        Book book = new Book();
        book.setTitle(titleField.getText().trim());
        book.setAuthor(authorField.getText().trim());
        book.setIsbn(isbnField.getText().trim());
        book.setPublishedDate(publishedDatePicker.getValue().toString());

        new Thread(() -> {
            try {
                Book updatedBook = bookService.updateBook(selectedBook.getId(), book);
                Platform.runLater(() -> {
                    loadBooksAsync(); // Reload to show updated book
                    clearForm();
                    updateStatus("Book updated successfully", true);
                });
            } catch (Exception ex) {
                Platform.runLater(() -> updateStatus("Error updating book: " + ex.getMessage(), false));
            }
        }).start();
    }

    /**
     * Handle the Delete button action.
     */
    private void handleDelete() {
        if (selectedBook == null) {
            updateStatus("Please select a book to delete", false);
            return;
        }

        // Confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Book");
        alert.setContentText("Are you sure you want to delete '" + selectedBook.getTitle() + "'?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            new Thread(() -> {
                try {
                    boolean deleted = bookService.deleteBook(selectedBook.getId());
                    Platform.runLater(() -> {
                        if (deleted) {
                            clearForm();

                            // If we deleted the last item on a page, go to previous page
                            if (tableView.getItems().size() == 1 && currentPage > 0) {
                                currentPage--;
                            }

                            loadBooksAsync();
                            updateStatus("Book deleted successfully", true);
                        } else {
                            updateStatus("Failed to delete book", false);
                        }
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> updateStatus("Error deleting book: " + ex.getMessage(), false));
                }
            }).start();
        }
    }

    /**
     * Handle the Search button action.
     */
    private void handleSearch() {
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            // If search is empty, just reload normal paginated view
            currentPage = 0;
            loadBooksAsync();
            return;
        }

        new Thread(() -> {
            try {
                List<Book> books = bookService.searchBooks(query);
                Platform.runLater(() -> {
                    tableView.getItems().clear();

                    // Convert Book to BookResponse for display
                    for (Book book : books) {
                        BookResponse response = new BookResponse();
                        response.setId(book.getId());
                        response.setTitle(book.getTitle());
                        response.setAuthor(book.getAuthor());
                        response.setIsbn(book.getIsbn());
                        if (book.getPublishedDate() != null) {
                            response.setPublishedDate(LocalDate.parse(book.getPublishedDate()));
                        }
                        tableView.getItems().add(response);
                    }

                    // Disable pagination during search
                    prevPageBtn.setDisable(true);
                    nextPageBtn.setDisable(true);
                    paginationLabel.setText("Search Results");

                    updateStatus("Found " + books.size() + " books", true);
                });
            } catch (Exception ex) {
                Platform.runLater(() -> updateStatus("Error searching: " + ex.getMessage(), false));
            }
        }).start();
    }

    /**
     * Handle the Refresh button action.
     */
    private void handleRefresh() {
        clearForm();
        searchField.clear();
        currentPage = 0;
        loadBooksAsync();
    }

    /**
     * Populate the form with the selected book's data.
     */
    private void populateForm(BookResponse book) {
        selectedBook = book;
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        isbnField.setText(book.getIsbn());

        if (book.getPublishedDate() != null) {
            try {
                publishedDatePicker.setValue(book.getPublishedDate());
            } catch (DateTimeParseException e) {
                publishedDatePicker.setValue(null);
            }
        } else {
            publishedDatePicker.setValue(null);
        }
    }

    /**
     * Clear the form fields.
     */
    private void clearForm() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        publishedDatePicker.setValue(null);
        selectedBook = null;
        tableView.getSelectionModel().clearSelection();
    }

    /**
     * Validate the form fields.
     */
    private boolean validateForm() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn = isbnField.getText().trim();
        LocalDate date = publishedDatePicker.getValue();

        if (title.isEmpty()) {
            updateStatus("Title is required", false);
            return false;
        }

        if (author.isEmpty()) {
            updateStatus("Author is required", false);
            return false;
        }

        if (isbn.isEmpty()) {
            updateStatus("ISBN is required", false);
            return false;
        }

        if (date == null) {
            updateStatus("Published date is required", false);
            return false;
        }

        return true;
    }

    /**
     * Update the status label.
     */
    private void updateStatus(String message, boolean success) {
        statusLabel.setText(message);
        statusLabel.setStyle(success ? "-fx-text-fill: #27ae60;" : "-fx-text-fill: #e74c3c;");
    }
}