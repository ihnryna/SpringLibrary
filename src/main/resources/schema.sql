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