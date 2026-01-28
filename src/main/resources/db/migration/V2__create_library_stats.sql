CREATE TABLE book_stats
(
    author_name           VARCHAR(255) PRIMARY KEY,
    books_count           INT NOT NULL,
    earliest_publish_year INT NOT NULL
);

INSERT INTO book_stats (author_name, books_count, earliest_publish_year)
SELECT author,
       COUNT(*),
       MIN(published_year)
FROM library_item
WHERE dtype = 'Book'
GROUP BY author;
