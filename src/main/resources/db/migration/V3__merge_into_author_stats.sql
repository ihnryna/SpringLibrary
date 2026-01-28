CREATE TABLE author_stats
(
    id                    BIGSERIAL PRIMARY KEY,
    author_name           VARCHAR(255) NOT NULL,
    country               VARCHAR(100),
    active                BOOLEAN      NOT NULL,
    books_count           INT          NOT NULL,
    earliest_publish_year INT          NOT NULL
);

INSERT INTO author_stats (author_name,
                          country,
                          active,
                          books_count,
                          earliest_publish_year)
SELECT ap.author_name,
       ap.country,
       ap.active,
       bs.books_count,
       bs.earliest_publish_year
FROM author_profile ap
         JOIN book_stats bs
              ON ap.author_name = bs.author_name;

DROP TABLE book_stats;
DROP TABLE author_profile;
