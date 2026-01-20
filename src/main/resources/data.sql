TRUNCATE TABLE book, magazine, library_item RESTART IDENTITY CASCADE;

-- Library items
INSERT INTO library_item (title, published_year, available) VALUES
                                                                ('Effective Java', 2018, true),
                                                                ('Clean Code', 2008, true),
                                                                ('National Geographic', 2023, true),
                                                                ('Science Monthly', 2024, false);

-- Books (потрібно дізнатися реальні id з library_item)
INSERT INTO book (id, isbn, author)
SELECT id, '978-0134685991', 'Joshua Bloch' FROM library_item WHERE title='Effective Java';

INSERT INTO book (id, isbn, author)
SELECT id, '978-0132350884', 'Robert C. Martin' FROM library_item WHERE title='Clean Code';

-- Magazines
INSERT INTO magazine (id, issue_number, month)
SELECT id, 202301, 'January' FROM library_item WHERE title='National Geographic';

INSERT INTO magazine (id, issue_number, month)
SELECT id, 202402, 'February' FROM library_item WHERE title='Science Monthly';

