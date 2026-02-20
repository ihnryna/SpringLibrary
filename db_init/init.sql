CREATE TABLE IF NOT EXISTS library_item (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    published_year INT NOT NULL,
    available BOOLEAN NOT NULL,
    isbn VARCHAR(20),
    author VARCHAR(255),
    issue_number INT,
    month VARCHAR(20),
    dtype VARCHAR(255)
    );
TRUNCATE TABLE library_item RESTART IDENTITY CASCADE;

CREATE TABLE IF NOT EXISTS author_stats (
                                            id SERIAL PRIMARY KEY,
                                            author_name TEXT,
                                            country TEXT,
                                            active BOOLEAN,
                                            books_count INT,
                                            earliest_publish_year INT
);

CREATE TABLE IF NOT EXISTS reader_account (
                                              id SERIAL PRIMARY KEY,
                                              surname TEXT,
                                              balance NUMERIC
);

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


INSERT INTO reader_account (surname, balance)
VALUES ('Hryshchenko', 50);
DO $$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_roles WHERE rolname = 'replicator'
   ) THEN
CREATE ROLE replicator WITH REPLICATION LOGIN PASSWORD 'replica_pass';
END IF;
END
$$;
