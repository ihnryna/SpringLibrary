CREATE TABLE library_item
(
    id             BIGSERIAL PRIMARY KEY,
    title          VARCHAR(255) NOT NULL,
    published_year INT          NOT NULL,
    available      BOOLEAN      NOT NULL,
    isbn           VARCHAR(20),
    author         VARCHAR(255),
    issue_number   INT,
    month          VARCHAR(20),
    dtype          VARCHAR(255)
);

INSERT INTO library_item (title, published_year, available, isbn, author, dtype)
VALUES ('Effective Java', 2018, true, '978-0134685991', 'Joshua Bloch', 'Book'),
       ('Clean Code', 2008, true, '978-0132350884', 'Robert C. Martin', 'Book');

INSERT INTO library_item (title, published_year, available, issue_number, month, dtype)
VALUES ('National Geographic', 2023, true, 202301, 'January', 'Magazine'),
       ('Science Monthly', 2004, false, 202402, 'February', 'Magazine');



CREATE TABLE author_profile
(
    id          BIGSERIAL PRIMARY KEY,
    author_name VARCHAR(255) NOT NULL,
    country     VARCHAR(100),
    active      BOOLEAN      NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO author_profile (author_name, country, active)
VALUES ('Joshua Bloch', 'USA', true),
       ('Robert C. Martin', 'USA', true),
       ('Unknown Author', 'Unknown', false);
