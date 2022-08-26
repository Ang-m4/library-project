-- Active: 1660964660150@@127.0.0.1@3306@library_db
INSERT INTO users (user_id, user_name, user_lastname, user_nickname, user_password, user_blocked, user_role) VALUES (1, 'Jane', 'doe', 'Jenny', '12345678', false, 'ROLE_ADMIN');
INSERT INTO users (user_id, user_name, user_lastname, user_nickname, user_password, user_blocked, user_role) VALUES (2, 'Mary', 'doe', 'Mary', '12345678', false, 'ROLE_LIBRARIAN');
INSERT INTO users (user_id, user_name, user_lastname, user_nickname, user_password, user_blocked, user_role) VALUES (3, 'John', 'doe', 'John', '12345678', false, 'ROLE_READER');
INSERT INTO users (user_id, user_name, user_lastname, user_nickname, user_password, user_blocked, user_role) VALUES (4, 'John', 'junior', 'Jhonnie', '12345678', false, 'ROLE_READER');

INSERT INTO books (book_isbn, book_name, book_author, book_publish_date, book_copies, book_publish) VALUES (00201, 'Harry Potter', 'J.K. Rowling', STR_TO_DATE('05/12/1934', '%m/%d/%Y'), 10, 'Vol. 2');
INSERT INTO books (book_isbn, book_name, book_author, book_publish_date, book_copies, book_publish) VALUES (00202, 'The Creator', 'A.K. Smith', STR_TO_DATE('01/03/1949', '%m/%d/%Y'), 10, 'Vol. 3');
INSERT INTO books (book_isbn, book_name, book_author, book_publish_date, book_copies, book_publish) VALUES (00203, 'The Lord of the Rings', 'J.R.R. Tolkien', STR_TO_DATE('05/02/1954', '%m/%d/%Y'), 10, 'Vol. 1');
INSERT INTO books (book_isbn, book_name, book_author, book_publish_date, book_copies, book_publish) VALUES (00204, 'The Hobbit', 'J.R.R. Tolkien', STR_TO_DATE('02/01/1954', '%m/%d/%Y'), 10, 'Vol. 1');

