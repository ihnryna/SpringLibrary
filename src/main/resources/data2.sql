TRUNCATE TABLE library_item RESTART IDENTITY CASCADE;

INSERT INTO library_item (title, published_year, available, isbn, author, dtype)
VALUES ('Effective Java', 2018, true,'978-0134685991', 'Joshua Bloch', 'Book');


INSERT INTO library_item (title, published_year, available, issue_number, month, dtype)
VALUES ('Not Fun Journal', 2026, false,202301, 'January', 'Magazine'),
       ('Fun Journal', 2001, true,202403, 'June', 'Magazine')
