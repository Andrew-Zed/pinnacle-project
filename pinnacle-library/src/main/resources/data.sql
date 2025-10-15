CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(15) NOT NULL UNIQUE,
    published_date DATE
);

INSERT INTO books (title, author, isbn, published_date)
VALUES
('Effective Java', 'Joshua Bloch', '9780134685991', '2018-01-11'),
('Clean Code', 'Robert C. Martin', '9780132350884', '2008-08-11'),
('Design Patterns: Elements of Reusable Object-Oriented Software', 'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides', '9780201633610', '1994-10-21'),
('Java Concurrency in Practice', 'Brian Goetz', '9780321349606', '2006-05-01'),
('Spring in Action', 'Craig Walls', '9781617294945', '2018-05-25'),
('The Pragmatic Programmer', 'Andrew Hunt, David Thomas', '9780201616225', '1999-10-30'), -- Changed ISBN
('Head First Java', 'Kathy Sierra, Bert Bates', '9780596009205', '2005-02-14'),
('Clean Architecture', 'Robert C. Martin', '9780134494166', '2017-09-22'),
('Java 8 in Action', 'Raoul-Gabriel Urma, Mario Fusco, Alan Mycroft', '9781617291999', '2018-01-28'),
('The Art of Computer Programming', 'Donald E. Knuth', '9780321751041', '1997-09-01'),
('Artificial Intelligence: A Modern Approach', 'Stuart Russell, Peter Norvig', '9780134610993', '2016-12-14'),
('The Clean Coder', 'Robert C. Martin', '9780137081073', '2011-05-12'),
('The Mythical Man-Month', 'Frederick P. Brooks Jr.', '9780201835953', '1995-07-01'),
('Introduction to Algorithms', 'Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein', '9780262033848', '2009-07-31'),
('Refactoring: Improving the Design of Existing Code', 'Martin Fowler', '9780201485677', '1999-06-08'),
('Code Complete', 'Steve McConnell', '9780735619678', '2004-06-09'),
('Java: The Complete Reference', 'Herbert Schildt', '9781260440232', '2018-09-04'),
('Mastering Regular Expressions', 'Jeffrey E. F. Friedl', '9780596528126', '2006-03-29'),
('Groovy in Action', 'Dierk Koenig, Paul King, Guillaume Laforge', '9781935182347', '2010-09-15'),
('Spring Microservices in Action', 'John Carnell', '9781617293986', '2017-11-10'),
('Java EE 8 Application Development', 'David R. Heffelfinger', '9781788621755', '2018-09-25'),
('Jenkins 2: Up and Running', 'Olivier Lamy, R. J. T. Wright', '9781098102512', '2020-10-20');
