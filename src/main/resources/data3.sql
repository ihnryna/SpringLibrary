TRUNCATE TABLE library_item RESTART IDENTITY CASCADE;
TRUNCATE TABLE author_stats RESTART IDENTITY CASCADE;


INSERT INTO library_item (title, published_year, available, isbn, author, dtype)
VALUES ('Effective Java', 2018, true, '978-0134685991', 'Joshua Bloch', 'Book'),
       ('Clean Code', 2008, true, '978-0132350884', 'Robert C. Martin', 'Book'),
       ('Clean Architecture', 2017, false, '978-0134494166', 'Robert C. Martin', 'Book'),
       ('Design Patterns', 1994, true, '978-0201633610', 'Erich Gamma', 'Book'),
       ('Refactoring', 2018, true, '978-0134757599', 'Martin Fowler', 'Book'),
       ('Java Concurrency in Practice', 2006, false, '978-0321349606', 'Brian Goetz', 'Book'),
       ('Spring in Action', 2021, true, '978-1617297571', 'Craig Walls', 'Book'),
       ('Hibernate Tips', 2018, true, '978-3960090484', 'Thorben Janssen', 'Book');


INSERT INTO library_item (title, published_year, available, issue_number, month, dtype)
VALUES ('National Geographic', 2023, true, 202301, 'January', 'Magazine'),
       ('Science Monthly', 2004, false, 202402, 'February', 'Magazine'),
       ('Time', 2022, true, 202210, 'October', 'Magazine'),
       ('Forbes', 2021, true, 202107, 'July', 'Magazine'),
       ('The Economist', 2020, false, 202012, 'December', 'Magazine'),
       ('Nature', 2024, true, 202403, 'March', 'Magazine');

INSERT INTO author_stats (author_name, country, active, books_count, earliest_publish_year)
VALUES ('Brian Goetz', 'USA', true, 1, 2006),
       ('Craig Walls', 'USA', true, 1, 2021),
       ('Erich Gamma', 'Switzerland', true, 1, 1994),
       ('Joshua Bloch', 'USA', true, 1, 2018),
       ('Martin Fowler', 'UK', true, 1, 2018),
       ('Robert C. Martin', 'USA', true, 2, 2008),
       ('Thorben Janssen', 'Germany', true, 1, 2018);