Library Management System
A complete JavaFX and Spring Boot application for managing library book records. The system features a user-friendly desktop UI and a robust REST API backend with H2 in-memory database.

________________________________________

**Prerequisites**
_________________________________________

Java 17 or higher
Maven 3.6.3 or higher
Git (optional, for cloning)

**Installation and Setup**

1. Clone or Download the Project

  git clone https://github.com/Andrew-Zed/pinnacle-project.git
  cd pinnacle-project

2. Backend Setup

   cd pinnacle-library
   mvn clean install
This will download all dependencies and compile the backend application.

3. Frontend Setup

Navigate to the frontend directory and build the project:
   cd ../PinnacleLibraryUI
   mvn clean install
   
This will download all dependencies and compile the frontend application.

_______________________________________________________________________________________________________________________________________________________________________________________

**Running the Application**
_______________________________________________________________________________________________________________________________________________________________________________________

1. **Start the Backend Server**
   From the pinnacle-library directory:
     mvn spring-boot:run
   The backend will start on http://localhost:8080.

2. **Start the Frontend Application**
   From the PinnacleLibraryUI directory (in a new terminal):
     mvn javafx:run
   The JavaFX application window will open with the Library Management System UI.

**Features**
Core Features

* View Books: TableView displays all books from the backend
* Add Book: Submit a form to create a new book record
* Update Book: Select a book and modify its details
* Delete Book: Remove books with confirmation dialog
* Search: Filter books by title or author name
* Refresh: Reload all books from the backend

Backend Features

REST API Endpoints:

POST /api/v1/books/add-book - Create a new book
GET /api/v1/books - Retrieve all books
GET /api/books/get-book/{id} - Retrieve a specific book
PUT /api/v1/books/update/{id} - Update a book
DELETE /api/v1/books/delete/{id} - Delete a book
GET /api/v1/books/search?query=xxx - Search books


Data Persistence: H2 in-memory database with automatic schema creation
Error Handling: Comprehensive exception handling with appropriate HTTP status codes
CORS Support: Cross-Origin Resource Sharing enabled for frontend communication

______________________________________
API Documentation
--------------------------------------

Add a Book
POST /api/v1/books/add-book
Content-Type: application/json

{
  "title": "Book Title",
  "author": "Author Name",
  "isbn": "978-0123456789",
  "publishedDate": "2023-01-15"
}

Response: 201 Created
{
  "id": 1,
  "title": "Book Title",
  "author": "Author Name",
  "isbn": "978-0123456789",
  "publishedDate": "2023-01-15"
}

Get All Books
GET /api/v1/books/get-all-books

Response: 200 OK
[
  {
    "id": 1,
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "isbn": "978-0743273565",
    "publishedDate": "1925-04-10"
  },
  ...
]
Update a Book
PUT /api/v1/books/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "author": "Updated Author",
  "isbn": "978-0123456789",
  "publishedDate": "2023-01-15"
}

Response: 200 OK
Delete a Book
DELETE /api/v1/books/{id}

Response: 204 No Content
Search Books
GET /api/books/search?query=gatsby

Response: 200 OK
[
  {
    "id": 1,
    "title": "The Great Gatsby",
    ...
  }
]

**Troubleshooting**
Backend won't start

Ensure port 8080 is not in use
Check that Java 17+ is installed: java -version
Try: mvn clean install and mvn spring-boot:run

Frontend can't connect to backend

Verify the backend is running on http://localhost:8080
Check the URL in BookService.java (BASE_URL)
Ensure no firewall is blocking communication

H2 Console Access
You can access the H2 database console at:
http://localhost:8080/h2-console
Default credentials:

URL: jdbc:h2:mem:librarydb
Username: sa
Password: (leave blank)

Technologies Used
Backend

Spring Boot 3.1.5
Spring Data JPA
H2 Database
Lombok
Maven

Frontend

JavaFX 17.0.1
Apache HttpClient 5
GSON (JSON processing)
Lombok
Maven

Notes

The database is reset on each application restart (H2 in-memory)
All dates are stored in ISO format (yyyy-MM-dd)
ISBN field has a unique constraint
All fields except published date are required
Search is case-insensitive
