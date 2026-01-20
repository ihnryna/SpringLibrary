CREATE TABLE IF NOT EXISTS library_item (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    published_year INT NOT NULL,
    available BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS book (
    id BIGINT PRIMARY KEY,
    isbn VARCHAR(20),
    author VARCHAR(255),
    CONSTRAINT fk_book_item
    FOREIGN KEY (id) REFERENCES library_item(id)
);

CREATE TABLE IF NOT EXISTS magazine (
    id BIGINT PRIMARY KEY,
    issue_number INT,
    month VARCHAR(20),
    CONSTRAINT fk_magazine_item
    FOREIGN KEY (id) REFERENCES library_item(id)
);
